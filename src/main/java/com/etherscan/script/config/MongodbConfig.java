package com.etherscan.script.config;

import com.etherscan.script.statemachine.Events;
import com.etherscan.script.statemachine.States;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.mongodb.MongoDbPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.mongodb.MongoDbStateMachineRepository;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
@EnableMongoRepositories(basePackages = {"org.springframework.statemachine.data.mongodb", "com.etherscan.script.mongo.repositories"})
@EntityScan(basePackages = {"org.springframework.statemachine.data.mongodb", "com.etherscan.script.entities"})
public class MongodbConfig
{
    @Bean
    public StateMachineRuntimePersister<States, Events, Long> mongoRuntimePersist(
        MongoDbStateMachineRepository repository)
    {
        return new MongoDbPersistingStateMachineInterceptor<>(repository);
    }

    @Bean
    public StateMachinePersister<States, Events, Long> defaultPersister(
        StateMachinePersist defaultPersist)
    {
        return new DefaultStateMachinePersister<>(defaultPersist);
    }
}
