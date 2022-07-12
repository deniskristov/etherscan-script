package com.etherscan.script.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.statemachine.data.mongodb.MongoDbRepositoryStateMachine;

public interface StateMachineRepository extends MongoRepository<MongoDbRepositoryStateMachine, String>
{
}
