package com.etherscan.script.statemachine;

import com.etherscan.script.statemachine.actions.SaveContractAction;
import com.etherscan.script.statemachine.actions.SaveRowNumberAction;
import com.etherscan.script.statemachine.actions.gui.*;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.xml.sax.ErrorHandler;

@RequiredArgsConstructor
@EnableStateMachineFactory
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<States, Events>
{
    private final MainMenuAction mainMenuAction;
    private final ContractRequestAction contractRequestAction;
    private final SaveContractAction saveContractAction;
    private final RowNumbersRequestAction rowNumbersRequestAction;
    private final SaveRowNumberAction saveRowNumberAction;
    private final ExtractEtherscanUrlAction extractEtherscanUrlAction;
    private final ErrorAction errorAction;

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception
    {
        states
            .withStates()
            .initial(States.INITIAL)
            .state(States.MAIN_MENU, mainMenuAction)
            .state(States.SET_CONTRACT, contractRequestAction)
            .state(States.SET_ROW_NUMBER, rowNumbersRequestAction);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception
    {
        transitions
            .withExternal()
                .source(States.INITIAL)
                .event(Events.MAIN_MENU).target(States.MAIN_MENU)
                .and()
            // Main menu actions
            .withExternal()
                .source(States.MAIN_MENU)
                .event(Events.SET_CONTRACT).target(States.SET_CONTRACT)
                .and()
            .withExternal()
                .source(States.MAIN_MENU)
                .event(Events.SET_ROW_NUMBER).target(States.SET_ROW_NUMBER)
                .and()
            .withInternal()
                .source(States.MAIN_MENU)
                .event(Events.START_SCAN)
                .action(extractEtherscanUrlAction)
                .and()

            .withExternal()
                .source(States.SET_CONTRACT)
                .event(Events.CONTRACT_RECEIVED).target(States.MAIN_MENU)
                .action(saveContractAction, errorAction)
                .and()
            .withExternal()
                .source(States.SET_ROW_NUMBER)
                .event(Events.ROW_NUMBER_RECEIVED).target(States.MAIN_MENU)
                .action(saveRowNumberAction, errorAction)
        ;
    }
}
