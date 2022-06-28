package com.etherscan.script.services;

import com.etherscan.script.entities.Contract;
import com.etherscan.script.event.events.UrlChangedEvent;
import com.etherscan.script.repositories.ContractRepository;
import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.Headers;
import com.etherscan.script.statemachine.StateMachinesHolder;
import com.etherscan.script.utils.SeleniumUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class EtherscanScheduledJob
{
    @Autowired
    @Lazy
    private StateMachinesHolder stateMachinesHolder;
    private final ContractRepository contractRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${etherscan.selenium.remoteWebDriverUrl}")
    private String remoteWebDriverUrl;

    private Map<Integer, WebDriver> drivers = new HashMap<>();

    @Scheduled(fixedDelay = 10000)
    public void scan(){
        List<Contract> enabledContracts = contractRepository.findAllByEnabledIsTrue();
        stopRemovedContracts(enabledContracts);
        enabledContracts.forEach(contract ->
        {
            if (drivers.containsKey(contract.getId())) {
                log.info("Starting etherscan parsing. Contract {}", contract);
                long start = System.currentTimeMillis();
                WebDriver driver = drivers.get(contract.getId());
                try
                {
                    Integer rowNumber = contract.getLineNumber();
                    driver.navigate().refresh();
                    Thread.sleep(3000);
                    String parsedUrl = SeleniumUtils.findUrl(driver, rowNumber);
                    long end = System.currentTimeMillis();
                    long duration = end - start;
                    log.info("Completed etherscan parsing. Duration: {} ms., Contract: {}, Parsed yfvURL: {}", duration, contract, parsedUrl);
                    String existingUrl = contract.getUrl();
                    if (StringUtils.hasText(parsedUrl) && !parsedUrl.equals(existingUrl)) {
                        contract.setUrl(parsedUrl);
                        contractRepository.save(contract);
                        if (StringUtils.hasText(existingUrl))
                        {
                            applicationEventPublisher.publishEvent(
                                UrlChangedEvent.builder()
                                    .newUrl(parsedUrl)
                                    .contract(contract.getContract())
                                    .build());
                        }
                    }
                }
                catch (Exception e)
                {
                    contract.setUrl("");
                    contractRepository.save(contract);
                    driver.quit();
                    drivers.remove(contract.getId());
                    log.error("Error refreshing contract " + contract, e);
//                    stateMachinesHolder.sendToAll(MessageBuilder
//                        .withPayload(Events.ERROR_NOTIFICATION)
//                        .setHeader(Headers.PAYLOAD.toString(), e.toString())
//                        .setHeader(Headers.CONTRACT.toString(), contract)
//                        .build());
                }

            } else {
                log.info("Creating new driver. URL: {} Contract: {}", remoteWebDriverUrl, contract);
                WebDriver webDriver = null;
                try
                {
                    webDriver = SeleniumUtils.create(remoteWebDriverUrl);
                    webDriver.get(String.format("https://etherscan.io/readContract?m=normal&a=%s&v=%s", contract.getContract(), contract.getContract()));
                    drivers.put(contract.getId(), webDriver);
                }
                catch (Exception e) {
                    if (webDriver != null) {
                        webDriver.quit();
                    }
                    log.error("Error creating web driver contract " + contract, e);
                    stateMachinesHolder.sendToAll(MessageBuilder
                        .withPayload(Events.ERROR_NOTIFICATION)
                        .setHeader(Headers.PAYLOAD.toString(), e.toString())
                        .setHeader(Headers.CONTRACT.toString(), contract)
                        .build());
                }
            }
        });
    }

    public Set<Integer> contractsInProcess() {
        return drivers.keySet();
    }

    @PreDestroy
    public void stop()
    {
        drivers.forEach((id, driver) -> driver.quit());
    }

    private void stopRemovedContracts(List<Contract> enabledContracts)
    {
        Set<Integer> enabledContractsId = enabledContracts.stream().map(Contract::getId).collect(Collectors.toSet());
        drivers.forEach((id, driver) ->
        {
            if (!enabledContractsId.contains(id)) {
                driver.quit();
                drivers.remove(id);
            }
        });
    }
}
