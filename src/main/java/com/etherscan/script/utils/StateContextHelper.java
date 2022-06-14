package com.etherscan.script.utils;

import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.States;
import org.springframework.statemachine.StateContext;

public class StateContextHelper
{
    public static Long getChatId(StateContext<States, Events> stateContext)
    {
        return stateContext.getExtendedState().get(StateContextVariables.CHAT_ID, Long.class);
    }

    public static String getContract(StateContext<States, Events> stateContext)
    {
        return stateContext.getExtendedState().get(StateContextVariables.CONTRACT, String.class);
    }

    public static Integer getRowNumber(StateContext<States, Events> stateContext)
    {
        return stateContext.getExtendedState().get(StateContextVariables.ROW_NUMBER, Integer.class);
    }
}