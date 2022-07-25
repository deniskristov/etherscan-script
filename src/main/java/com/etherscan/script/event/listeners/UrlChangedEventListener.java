package com.etherscan.script.event.listeners;

import com.etherscan.script.event.events.UrlChangedEvent;
import com.etherscan.script.services.UrlService;
import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.Headers;
import com.etherscan.script.statemachine.StateMachinesHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UrlChangedEventListener
{
    private final StateMachinesHolder stateMachinesHolder;
    private final UrlService urlParserService;

    @EventListener
    public void handleUrlChangedEvent(UrlChangedEvent event)
    {
        stateMachinesHolder.sendToAll(MessageBuilder
            .withPayload(Events.URL_UPDATE_NOTIFICATION)
            .setHeader(Headers.PAYLOAD.toString(), event.getNewUrl())
            .setHeader(Headers.CONTRACT.toString(), event.getContract())
            .build());
        // TODO move to a separate async listener
        List<Integer> numbersWithKeyWords
            = urlParserService.findKeyWordsFromStartToEnd(event.getNewUrl(), event.getContract().getKeyWord(),
            event.getContract().getStart(), event.getContract().getEnd());
        stateMachinesHolder.sendToAll(MessageBuilder
            .withPayload(Events.KEY_WORDS_FOUND_NOTIFICATION)
            .setHeader(Headers.PAYLOAD.toString(), numbersWithKeyWords)
            .setHeader(Headers.CONTRACT.toString(), event.getContract())
            .build());
    }
}
