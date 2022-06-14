package com.etherscan.script.statemachine.actions.gui;

import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.States;
import com.etherscan.script.utils.StateContextHelper;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class ErrorAction  extends AbstractTelegramAction
{
    @Override
    public void execute(StateContext<States, Events> stateContext)
    {
        Exception exception = stateContext.getException();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(StateContextHelper.getChatId(stateContext).toString());
        sendMessage.setText("Ошибка: " + exception.toString());
        execute(sendMessage);
    }
}
