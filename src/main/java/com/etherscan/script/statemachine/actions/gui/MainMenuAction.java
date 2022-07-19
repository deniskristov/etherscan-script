package com.etherscan.script.statemachine.actions.gui;

import com.etherscan.script.entities.Contract;
import com.etherscan.script.jobs.EtherscanScheduledJob;
import com.etherscan.script.repositories.ContractRepository;
import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.States;
import com.etherscan.script.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;

@Component
public class MainMenuAction extends AbstractTelegramAction
{
    @Autowired
    private EtherscanScheduledJob scheduledJob;
    @Autowired
    private ContractRepository contractRepository;

    @Override
    public void execute(StateContext<States, Events> stateContext)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(StateContextHelper.getChatId(stateContext).toString());
        StringBuilder text = new StringBuilder("Контракты в обработке:\n");
        scheduledJob.contractsInProcess().forEach(contractId -> {
            Contract contract = contractRepository.findById(contractId).get();
            text
                .append(Emoji.clipboard() + " ").append(contract.getName()).append("\n");
            text
                .append(Emoji.memo() + " ").append(contract.getContract()).append("\n");
            text
                .append("Последнее обновление: \n");

            if (contract.getLastUpdated() != null)
            {
                text.append(contract.isSuccessLastUpdate() ? Emoji.checkBoxOk() : Emoji.crossMark()).append(
                    DateUtils.format(contract.getLastUpdated(), DateUtils.DATE_TIME_FORMAT_GENERAL)).append("\n");
            }
            String url = contract.getUrl();
            if (StringUtils.hasText(url))
            {
                 text.append(url).append("\n\n");
            }
            else
            {
                text.append("URL: не распарсен").append("\n\n");
            }
        });
        sendMessage.setText(text.toString());
        sendMessage.setReplyMarkup(UtilityFunctions.createInlineKeyboard(
            new ArrayList<>() {{
                add(MenuBuilder.checkCurrentContracts());
            }}
        ));
        execute(sendMessage);
    }
}
