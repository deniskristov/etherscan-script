package com.etherscan.script.utils;

import com.etherscan.script.statemachine.Events;

public class MenuBuilder
{
    public static Object[] checkCurrentContracts()
    {
        return new Object[]{Emoji.reload() + " Обновить данные", Events.MAIN_MENU};
    }
}
