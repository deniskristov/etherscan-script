package com.etherscan.script;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@RequiredArgsConstructor
@Component
public class EtherscanBot extends TelegramLongPollingBot
{
    private final MessageProcessor messageProcessor;

    @Getter
    @Value("${bot.token}")
    private String botToken;
    @Getter
    @Value("${bot.username}")
    private String botUsername;

    @Override
    public void onUpdateReceived(Update update)
    {
        messageProcessor.process(update);
    }
}
