package com.etherscan.script.utils;

import com.etherscan.script.statemachine.Events;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UtilityFunctions
{
    private static final Pattern BAIL_ID_PATTERN = Pattern.compile("^/[0-9]+");

    public static boolean isBailIdCommand(String text)
    {
        return StringUtils.hasText(text) && BAIL_ID_PATTERN.matcher(text).matches();
    }

    public static String bailIdCommandToBailId(String text)
    {
        return text.replace("/", "");
    }

    public static InlineKeyboardMarkup createInlineKeyboard(List<Object[]> callbackToTextMap)
    {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        callbackToTextMap.forEach(entry ->
        {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            if (entry[1] instanceof Events)
            {
                rowInline.add(InlineKeyboardButton.builder()
                    .text(entry[0].toString())
                    .callbackData(entry[1].toString())
                    .build());
            }
            else if (entry[1] instanceof String)
            {
                if (EventUtils.isEventWithPayload(entry[1].toString()))
                {
                    rowInline.add(InlineKeyboardButton.builder()
                        .text(entry[0].toString())
                        .callbackData(entry[1].toString())
                        .build());
                }
                else
                {
                    rowInline.add(InlineKeyboardButton.builder()
                        .text(entry[0].toString())
                        .url(entry[1].toString())
                        .build());
                }
            }
            rowsInline.add(rowInline);
        });
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
