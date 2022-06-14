package com.etherscan.script.utils;

import com.etherscan.script.statemachine.Events;

public class MenuBuilder
{
    public static Object[] setContractButton()
    {
        return new Object[]{"Указать контракт", Events.SET_CONTRACT};
    }

    public static Object[] setRowNumberButton()
    {
        return new Object[]{"Указать номер строки", Events.SET_ROW_NUMBER};
    }

    public static Object[] startScan()
    {
        return new Object[]{"Начать", Events.START_SCAN};
    }
}
