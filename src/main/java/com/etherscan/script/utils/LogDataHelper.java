package com.etherscan.script.utils;

import org.slf4j.MDC;
import org.springframework.statemachine.state.State;

public class LogDataHelper
{
    private static final String STATE = "state";
    private static final String CHAT_ID = "chatId";

    public static void put(String key, String value)
    {
        MDC.put(key, value);
    }

    public static void putSate(State value)
    {
        MDC.put(STATE, value == null || value.getId() == null
            ? "-"
            : value.getId().toString());
    }

    public static void putChatId(Long value)
    {
        MDC.put(CHAT_ID, String.valueOf(value));
    }

    public static void clear()
    {
        MDC.clear();
    }
}
