package com.etherscan.script.utils;

import com.etherscan.script.statemachine.Events;
import org.springframework.util.StringUtils;

public class EventUtils
{
    private static final String DELIMITER = "::";
    private static final String EVENT_WITH_PAYLOAD_PATTERN = "[A-Z_]*::.*";

    public static String append(Events event, String payload)
    {
        if (StringUtils.hasText(payload))
        {
            return event.toString() + DELIMITER + payload;
        }
        return event.toString();
    }

    public static Events parseEvent(String str)
    {
        if (str.contains(DELIMITER))
        {
            String[] parts = str.split(DELIMITER);
            if (parts.length == 2)
            {
                return Events.valueOf(parts[0]);
            }
            throw new IllegalStateException("Cannot build event. Original string '" + str + "'");
        }
        return Events.valueOf(str);
    }

    public static String parsePayload(String str)
    {
        if (str.contains(DELIMITER))
        {
            String[] parts = str.split(DELIMITER);
            if (parts.length == 2)
            {
                return parts[1];
            }
            throw new IllegalStateException("Cannot parse payload. Original string '" + str + "'");
        }
        return "";
    }

    public static boolean isEventWithPayload(String str)
    {
        return str.matches(EVENT_WITH_PAYLOAD_PATTERN);
    }
}
