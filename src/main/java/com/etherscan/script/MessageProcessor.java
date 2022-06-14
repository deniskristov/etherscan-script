package com.etherscan.script;

import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.Headers;
import com.etherscan.script.statemachine.StateMachinesHolder;
import com.etherscan.script.statemachine.States;
import com.etherscan.script.utils.EventUtils;
import com.etherscan.script.utils.LogDataHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageProcessor
{
    private final StateMachinesHolder stateMachinesHolder;

    public void process(Update update)
    {
        Long chatId = getChatId(update);
        if (chatId == null)
            return;
        try
        {
            LogDataHelper.putChatId(chatId);
            StateMachine stateMachine = stateMachinesHolder.getOrStart(chatId, true);
            LogDataHelper.putSate(stateMachine.getState());
            if (stateMachine.getState().getId() == States.INITIAL)
            {
                stateMachine.sendEvent(Events.MAIN_MENU);
            }
            if (update.hasCallbackQuery())
            {
                stateMachine.sendEvent(
                    MessageBuilder
                        .withPayload(EventUtils.parseEvent(update.getCallbackQuery().getData()))
                        .setHeader(Headers.CALLBACK_QUERY_MESSAGE_ID.toString(), update.getCallbackQuery().getMessage().getMessageId())
                        .setHeader(Headers.PAYLOAD.toString(), EventUtils.parsePayload(update.getCallbackQuery().getData()))
                        .build()
                );
            }
            else if (update.getMessage().hasText())
            {
                // TODO refactor this (get actions by state?)
                if (stateMachine.getState().getId() == States.SET_CONTRACT)
                {
                    stateMachine.sendEvent(
                        MessageBuilder
                            .withPayload(Events.CONTRACT_RECEIVED)
                            .setHeader(Headers.PAYLOAD.toString(), update.getMessage().getText().trim())
                            .build()
                    );
                }
                else if (stateMachine.getState().getId() == States.SET_ROW_NUMBER)
                {
                    stateMachine.sendEvent(
                        MessageBuilder
                            .withPayload(Events.ROW_NUMBER_RECEIVED)
                            .setHeader(Headers.PAYLOAD.toString(), update.getMessage().getText().trim())
                            .build()
                    );
                }
                else
                {
                    stateMachine.sendEvent(Events.MAIN_MENU);
                }
            }
        }
        finally
        {
            LogDataHelper.clear();
        }
    }

    private Long getChatId(Update update)
    {
        if (update.hasCallbackQuery()) return update.getCallbackQuery().getMessage().getChatId();
        // ChatId is a unique identifier for a chat, that can be either private , group,
        // supergroup or channel whereas userId is a unique identifier for a user or bot only.
        // The only time the values can be the same is in a private chat. Read more about Telegram types here
        // So in this case it is possible to use user id as chat id.
        if (update.getPreCheckoutQuery() != null) return update.getPreCheckoutQuery().getFrom().getId();
        if (update.getMessage() != null) return update.getMessage().getChatId();
        log.warn("Невозможно определить ID пользователя " + update);
        return null;
    }
}
