package com.etherscan.script.statemachine.actions.gui;

import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.States;
import com.etherscan.script.utils.StateContextHelper;
import com.etherscan.script.utils.MenuBuilder;
import com.etherscan.script.utils.UtilityFunctions;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;

@Component
public class MainMenuAction extends AbstractTelegramAction
{
    @Override
    public void execute(StateContext<States, Events> stateContext)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(StateContextHelper.getChatId(stateContext).toString());
        String contract = StateContextHelper.getContract(stateContext);
        Integer rowNumber = StateContextHelper.getRowNumber(stateContext);
        sendMessage.setText(
            String.format(
                "Текущий контракт: %s\n" +
                "Текущая строка: %s\n",
                StringUtils.hasText(contract) ? contract : "не задан",
                rowNumber != null ? rowNumber : "не задана"
            ));
        sendMessage.setReplyMarkup(UtilityFunctions.createInlineKeyboard(
            new ArrayList<>() {{
                add(MenuBuilder.setContractButton());
                add(MenuBuilder.setRowNumberButton());
                add(MenuBuilder.startScan());
            }}
        ));
        execute(sendMessage);
    }
}
