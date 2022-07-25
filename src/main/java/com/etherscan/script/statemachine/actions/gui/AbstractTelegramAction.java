package com.etherscan.script.statemachine.actions.gui;

import com.etherscan.script.EtherscanBot;
import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.statemachine.action.Action;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
public abstract class AbstractTelegramAction implements Action<States, Events>
{
    @Lazy
    @Autowired
    private EtherscanBot bot;

    protected Message execute(SendMessage sendMessage)
    {
        try
        {
            return bot.execute(sendMessage);
        }
        catch (Exception e)
        {
            log.error("Error sending message: " + sendMessage.toString() + " Original error: " + e.toString(), e);
            return null;
        }
    }
}
