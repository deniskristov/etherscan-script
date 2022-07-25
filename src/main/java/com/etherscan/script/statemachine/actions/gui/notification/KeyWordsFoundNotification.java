package com.etherscan.script.statemachine.actions.gui.notification;

import com.etherscan.script.entities.Contract;
import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.Headers;
import com.etherscan.script.statemachine.States;
import com.etherscan.script.statemachine.actions.gui.AbstractTelegramAction;
import com.etherscan.script.utils.Emoji;
import com.etherscan.script.utils.HeaderHelper;
import com.etherscan.script.utils.StateContextHelper;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class KeyWordsFoundNotification extends AbstractTelegramAction
{
    @Override
    public void execute(StateContext<States, Events> stateContext)
    {
        SendMessage linkMessage = new SendMessage();
        linkMessage.enableMarkdown(true);
        linkMessage.setChatId(StateContextHelper.getChatId(stateContext).toString());
        Contract.Dto contract = HeaderHelper.getContract(stateContext);
        List<Integer> foundInNumbers = HeaderHelper.getAsListInteger(stateContext, Headers.PAYLOAD);
        if (foundInNumbers.size() > 0)
        {
            linkMessage.setText(
                Emoji.clipboard() + contract.getName() + "\n" +
                    Emoji.heavyCheckMark() + " Ключевое слово " + contract.getKeyWord() + " найдено в номерах:\n" +
                    foundInNumbers.toString());
        }
        else
        {
            linkMessage.setText(
                Emoji.clipboard() + contract.getName() + "\n" +
                    Emoji.crossMark() + " Ключевое слово " + contract.getKeyWord() + " не найдено во всем диапазоне");
        }
        execute(linkMessage);
    }
}
