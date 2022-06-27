package com.etherscan.script.controllers;

import com.etherscan.script.entities.Contract;
import com.etherscan.script.repositories.ContractRepository;
import com.etherscan.script.utils.DataTableDS;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/contracts")
public class ContractController
{
    private final ContractRepository contractRepository;

    @GetMapping(path = "/{id}")
    public Contract.Dto get(@PathVariable Integer id)
    {
        return contractRepository.findById(id)
            .map(Contract.Dto::fromEntity)
            .orElseThrow(() -> new NoSuchElementException());
    }

    @PostMapping
    public void create(@RequestBody Contract.Dto dto)
    {
        contractRepository.save(dto.createEntity());
    }

    @PutMapping
    public void update(@RequestBody Contract.Dto dto)
    {
        Contract contract = contractRepository.findById(dto.getId()).get();
//        contract.update(dto);
        contractRepository.save(contract);
    }

    @GetMapping("/datasource")
    public DataTableDS dataSource(
        @RequestParam(value = "sEcho") final Integer sEcho)
    {
        DataTableDS dataSource = new DataTableDS();
        dataSource.setsEcho(sEcho);
        dataSource.setAaData(
            contractRepository.findAll().stream()
                .map(Contract.Dto::fromEntity)
                .collect(Collectors.toList()));
        return dataSource;
    }

    @DeleteMapping(path = "/{id}")
    public void delete(
        @PathVariable Integer id)
    {
        contractRepository.deleteById(id);
    }

    @PostMapping(path = "/{id}/toggle")
    public void run(
        @PathVariable Integer id)
    {
        Contract contract = contractRepository.findById(id).get();
        contract.setEnabled(!contract.isEnabled());
        contractRepository.save(contract);
    }
}
