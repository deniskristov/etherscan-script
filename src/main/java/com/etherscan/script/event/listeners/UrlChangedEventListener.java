package com.etherscan.script.event.listeners;

import com.etherscan.script.event.events.UrlChangedEvent;
import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.Headers;
import com.etherscan.script.statemachine.StateMachinesHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlChangedEventListener
{
    private final StateMachinesHolder stateMachinesHolder;

    @EventListener
    public void handleUrlChangedEvent(UrlChangedEvent event)
    {
        stateMachinesHolder.sendToAll(MessageBuilder
            .withPayload(Events.URL_UPDATE_NOTIFICATION)
            .setHeader(Headers.PAYLOAD.toString(), event.getNewUrl())
            .setHeader(Headers.CONTRACT.toString(), event.getContract())
            .build());
    }
}
