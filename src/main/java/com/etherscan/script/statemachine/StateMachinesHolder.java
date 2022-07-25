package com.etherscan.script.statemachine;

import com.etherscan.script.mongo.repositories.StateMachineRepository;
import com.etherscan.script.utils.StateContextVariables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class StateMachinesHolder
{
    @Autowired
    private StateMachineRepository stateMachineRepository;
    @Autowired
    private StateMachinePersister<States, Events, Long> stateMachinePersister;
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
                stateMachinePersister.restore(stateMachine, chatId);
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
                stateMachinePersister.persist(stateMachine, entry.getKey());
            }
            catch (Exception e)
            {
                log.error("Persisting state machine error:", e);
            }
        });
    }

    public void sendToAll(Message message)
    {
        stateMachineMap.keySet().forEach(chatId -> {
            stateMachineMap.get(chatId).sendEvent(message);
        });
        stateMachineRepository.findAll().stream()
            .filter(mongoDbRepositoryStateMachine -> !stateMachineMap.containsKey(Long.valueOf(mongoDbRepositoryStateMachine.getId())))
            .forEach(mongoDbRepositoryStateMachine ->
        {
            StateMachine stateMachine = getOrStart(Long.valueOf(mongoDbRepositoryStateMachine.getId()), true);
            stateMachine.sendEvent(message);
        });
    }

    private void beforeSave(StateMachine stateMachine)
    {

    }
}
