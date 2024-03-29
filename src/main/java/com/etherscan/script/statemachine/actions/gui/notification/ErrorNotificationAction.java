package com.etherscan.script.statemachine.actions.gui.notification;

import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.Headers;
import com.etherscan.script.statemachine.States;
import com.etherscan.script.statemachine.actions.gui.AbstractTelegramAction;
import com.etherscan.script.utils.HeaderHelper;
import com.etherscan.script.utils.StateContextHelper;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class ErrorNotificationAction extends AbstractTelegramAction
{
    @Override
    public void execute(StateContext<States, Events> stateContext)
    {
        SendMessage linkMessage = new SendMessage();
        linkMessage.enableMarkdown(true);
        linkMessage.setChatId(StateContextHelper.getChatId(stateContext).toString());
        linkMessage.setText(
            "Ошибка обработки контракта: " + HeaderHelper.getAsString(stateContext, Headers.CONTRACT) + "\n" +
                "Детали: " + HeaderHelper.getAsString(stateContext, Headers.PAYLOAD)
        );
        execute(linkMessage);
    }
}
