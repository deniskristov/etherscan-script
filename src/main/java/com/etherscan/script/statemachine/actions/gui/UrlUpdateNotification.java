package com.etherscan.script.statemachine.actions.gui;

import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.Headers;
import com.etherscan.script.statemachine.States;
import com.etherscan.script.utils.Emoji;
import com.etherscan.script.utils.HeaderHelper;
import com.etherscan.script.utils.StateContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
public class UrlUpdateNotification extends AbstractTelegramAction
{
    @Override
    public void execute(StateContext<States, Events> stateContext)
    {
        SendMessage linkMessage = new SendMessage();
        linkMessage.enableMarkdown(true);
        linkMessage.setChatId(StateContextHelper.getChatId(stateContext).toString());
        linkMessage.setText(Emoji.heavyCheckMark() + " Внимание! Изменилась ссылка\n" +
            Emoji.memo() + " Контракт: " + HeaderHelper.getAsString(stateContext, Headers.CONTRACT) + "\n" +
            HeaderHelper.getAsString(stateContext, Headers.PAYLOAD)
        );
        execute(linkMessage);
    }
}