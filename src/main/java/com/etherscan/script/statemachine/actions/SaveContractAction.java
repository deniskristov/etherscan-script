package com.etherscan.script.statemachine.actions;

import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.Headers;
import com.etherscan.script.statemachine.States;
import com.etherscan.script.utils.StateContextVariables;
import com.etherscan.script.utils.HeaderHelper;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class SaveContractAction implements Action<States, Events>
{
    @Override
    public void execute(StateContext<States, Events> stateContext)
    {
        stateContext.getExtendedState().getVariables().put(
            StateContextVariables.CONTRACT,
            HeaderHelper.getAsString(stateContext, Headers.PAYLOAD)
        );
    }
}
