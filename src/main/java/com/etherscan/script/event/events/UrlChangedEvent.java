package com.etherscan.script.event.events;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UrlChangedEvent
{
    private String newUrl;
    private String contract;
}
