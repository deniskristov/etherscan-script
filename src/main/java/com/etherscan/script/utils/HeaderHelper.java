package com.etherscan.script.utils;

import com.etherscan.script.entities.Contract;
import com.etherscan.script.statemachine.Headers;
import org.springframework.statemachine.StateContext;

import java.util.List;

public class HeaderHelper
{
    public static Integer getAsInteger(StateContext stateContext, Headers header)
    {
        return Integer.valueOf(stateContext.getMessageHeader(header.toString()).toString());
    }

    public static String getAsString(StateContext stateContext, Headers header)
    {
        return stateContext.getMessageHeader(header.toString()).toString();
    }

    public static List<Integer> getAsListInteger(StateContext stateContext, Headers header)
    {
        return (List<Integer>)stateContext.getMessageHeader(header.toString());
    }

    public static Contract.Dto getContract(StateContext stateContext)
    {
        return (Contract.Dto)stateContext.getMessageHeader(Headers.CONTRACT);
    }
}
