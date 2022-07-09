package com.etherscan.script.services;

import com.etherscan.script.entities.Contract;
import com.etherscan.script.event.events.UrlChangedEvent;
import com.etherscan.script.repositories.ContractRepository;
import com.etherscan.script.utils.SeleniumUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class EtherscanScheduledJob
{
    private final ContractRepository contractRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final static int MAX_ERROR_COUNT = 5;

    @Value("${etherscan.selenium.remoteWebDriverUrl}")
    private String remoteWebDriverUrl;

    private WebDriver webDriver = null;
    private Map<Integer, String> windows = new HashMap<>();
    private Integer errorCount = 0;

    @Scheduled(fixedDelay = 10000)
    public void scan(){
        if (errorCount > MAX_ERROR_COUNT) {
            log.error("MAX_ERROR limit is exseeded, resetting web driver.");
            webDriver = null;
            windows.clear();
        }
        if (webDriver == null) {
            createDriver();
        }
        if (webDriver == null) {
            return;
        }
        List<Contract> enabledContracts = contractRepository.findAllByEnabledIsTrue();
        stopRemovedContracts(enabledContracts);
        enabledContracts.forEach(contract ->
        {
            if (windows.containsKey(contract.getId())) {
                log.info("Starting etherscan parsing. Contract {}", contract);
                long start = System.currentTimeMillis();
                try
                {
                    webDriver.switchTo().window(windows.get(contract.getId()));
                    Thread.sleep(3000);
                    Integer rowNumber = contract.getLineNumber();
                    Optional<String> parsedUrl = SeleniumUtils.updateUri(webDriver, rowNumber);
                    long end = System.currentTimeMillis();
                    long duration = end - start;
                    log.info("Completed etherscan parsing. Duration: {} ms., Contract: {}, Parsed URL: {}", duration, contract, parsedUrl.orElse("parsing error"));
                    String existingUrl = contract.getUrl();
                    if (parsedUrl.isPresent())
                    {
                        if (errorCount > 0)
                        {
                            errorCount = 0;
                        }
                        if (!contract.isSuccessLastUpdate())
                        {
                            contract.setSuccessLastUpdate(true);
                            contractRepository.save(contract);
                        }
                        if (!parsedUrl.get().equals(existingUrl))
                        {
                            contract.setUrl(parsedUrl.get());
                            contractRepository.save(contract);
                            if (StringUtils.hasText(existingUrl))
                            {
                                applicationEventPublisher.publishEvent(
                                    UrlChangedEvent.builder()
                                        .newUrl(parsedUrl.get())
                                        .contract(Contract.Dto.fromEntity(contract))
                                        .build());
                            }
                        }
                    }
                    else
                    {
                        contract.setSuccessLastUpdate(false);
                        contractRepository.save(contract);
                    }
                }
                catch (Exception e)
                {
                    errorCount++;
                    contract.setSuccessLastUpdate(false);
                    contractRepository.save(contract);
                    log.error("Error reading contract " + contract, e);
                }
            } else {
                try
                {
                    log.info("Open new window. Contract: {}",  contract);
                    webDriver.switchTo().newWindow(WindowType.TAB);
                    webDriver.get(String.format("https://etherscan.io/readContract?m=normal&a=%s&v=%s", contract.getContract(), contract.getContract()));
                    Optional<String> parsedUrl = SeleniumUtils.findUriFirstTime(webDriver, contract.getLineNumber());
                    contract.setSuccessLastUpdate(true);
                    contract.setUrl(parsedUrl.get());
                    contractRepository.save(contract);
                    windows.put(contract.getId(), webDriver.getWindowHandle());
                }
                catch (Exception e)
                {
                    errorCount++;
                    log.error("Error opening new window " + contract, e);
                }
            }
        });
    }

    public Set<Integer> contractsInProcess() {
        return windows.keySet();
    }

    private void createDriver() {
        try
        {
            webDriver = SeleniumUtils.create(remoteWebDriverUrl);
        }
        catch (Exception e) {
            if (webDriver != null) {
                webDriver.quit();
                webDriver = null;
            }
            log.error("Error creating web driver", e);
        }
    }

    @PreDestroy
    public void stop()
    {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private void stopRemovedContracts(List<Contract> enabledContracts)
    {
        Set<Integer> enabledContractsId = enabledContracts.stream().map(Contract::getId).collect(Collectors.toSet());
        windows.forEach((id, windowName) ->
        {
            if (!enabledContractsId.contains(id)) {
                webDriver.switchTo().window(windowName);
                webDriver.close();
                windows.remove(id);
            }
        });
    }
}
