package com.etherscan.script.statemachine;

import com.etherscan.script.utils.StateContextVariables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class StateMachinesHolder
{
//    @Autowired
//    private StateMachinePersister<States, Events, Long> stateMachinePersister;
    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;

    private Map<Long, StateMachine> stateMachineMap = new HashMap<>();

    public StateMachine getOrStart(Long chatId, boolean tryRestore)
    {
        if (stateMachineMap.containsKey(chatId))
        {
            return stateMachineMap.get(chatId);
        }
        else
        {
            return start(chatId, tryRestore);
        }
    }

    private StateMachine start(Long chatId, boolean tryRestore)
    {
        StateMachine stateMachine = stateMachineFactory.getStateMachine(chatId.toString());
        if (tryRestore)
        {
            try
            {
//                stateMachinePersister.restore(stateMachine, chatId);
                stateMachine.start();
            }
            catch (Exception e)
            {
                log.error("Restoring state machine error:", e);
            }
        }
        stateMachine.getExtendedState().getVariables().put(StateContextVariables.CHAT_ID, chatId);
        stateMachineMap.put(chatId, stateMachine);
        return stateMachine;
    }

    @PreDestroy
    public void saveAll()
    {
        log.info(String.format("Persisting %d state machines.",stateMachineMap.size()));
        stateMachineMap.entrySet().forEach(entry ->
        {
            try
            {
                StateMachine stateMachine = entry.getValue();
                beforeSave(stateMachine);
//                stateMachinePersister.persist(stateMachine, entry.getKey());
            }
            catch (Exception e)
            {
                log.error("Persisting state machine error:", e);
            }
        });
    }

    private void beforeSave(StateMachine stateMachine)
    {

    }
}
