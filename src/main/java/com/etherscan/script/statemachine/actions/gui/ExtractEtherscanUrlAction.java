package com.etherscan.script.statemachine.actions.gui;

import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.States;
import com.etherscan.script.services.EtherscanService;
import com.etherscan.script.utils.StateContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
public class ExtractEtherscanUrlAction extends AbstractTelegramAction
{
    @Autowired
    private EtherscanService etherscanService;

    @Override
    public void execute(StateContext<States, Events> stateContext)
    {
        String url = etherscanService.extractUrl(StateContextHelper.getRowNumber(stateContext));
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(StateContextHelper.getChatId(stateContext).toString());
        sendMessage.setText("Начальная ссылка: " + url);
        execute(sendMessage);
    }
}
