package com.etherscan.script.event.events;

import com.etherscan.script.entities.Contract;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UrlChangedEvent
{
    private String newUrl;
    private Contract.Dto contract;
}
