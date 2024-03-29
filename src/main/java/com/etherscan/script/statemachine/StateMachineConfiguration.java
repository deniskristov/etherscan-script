package com.etherscan.script.statemachine;

import com.etherscan.script.statemachine.actions.gui.MainMenuAction;
import com.etherscan.script.statemachine.actions.gui.notification.ErrorNotificationAction;
import com.etherscan.script.statemachine.actions.gui.notification.KeyWordsFoundNotification;
import com.etherscan.script.statemachine.actions.gui.notification.UrlUpdateNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@RequiredArgsConstructor
@EnableStateMachineFactory
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<States, Events>
{
    private final MainMenuAction mainMenuAction;
    private final KeyWordsFoundNotification keyWordsFoundNotification;
    private final UrlUpdateNotification urlUpdateNotification;
    private final ErrorNotificationAction errorNotificationAction;

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception
    {
        states
            .withStates()
            .initial(States.INITIAL)
            .state(States.MAIN_MENU);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception
    {
        transitions
            .withExternal()
                .source(States.INITIAL)
                .event(Events.MAIN_MENU)
                .target(States.MAIN_MENU)
                .action(mainMenuAction)
                .and()
            // Main menu actions
            .withInternal()
                .source(States.MAIN_MENU)
                .event(Events.URL_UPDATE_NOTIFICATION)
                .action(urlUpdateNotification)
                .and()
            .withInternal()
                .source(States.MAIN_MENU)
                .event(Events.KEY_WORDS_FOUND_NOTIFICATION)
                .action(keyWordsFoundNotification)
                .and()
            .withInternal()
                .source(States.MAIN_MENU)
                .event(Events.ERROR_NOTIFICATION)
                .action(errorNotificationAction)
                .and()
            .withInternal()
                .source(States.MAIN_MENU)
                .event(Events.MAIN_MENU)
                .action(mainMenuAction)
                .and()
        ;
    }
}
