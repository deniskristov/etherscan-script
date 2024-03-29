package com.etherscan.script.repositories;

import com.etherscan.script.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer>
{
    List<Contract> findAllByEnabledIsTrue();
}
