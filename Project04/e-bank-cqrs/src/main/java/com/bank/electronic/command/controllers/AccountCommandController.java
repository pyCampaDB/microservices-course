package com.bank.electronic.command.controllers;

import com.bank.electronic.commonapi.commands.CreateAccountCommand;
import com.bank.electronic.commonapi.dtos.CreateAccountRequestDTO;
import com.bank.electronic.commonapi.dtos.CreditAccountRequestDTO;
import com.bank.electronic.commonapi.dtos.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/account")
public class AccountCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public CompletableFuture<String> createNewAccount(@RequestBody CreateAccountRequestDTO createAccountRequestDTO){
        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                createAccountRequestDTO.getCurrency(),
                createAccountRequestDTO.getInitialBalance()
        ));
    }

    @PostMapping("/debit")
    public CompletableFuture<String> placeDebit(@RequestBody DebitAccountRequestDTO debitAccountRequestDTO){
        return commandGateway.send(new CreateAccountCommand(
                debitAccountRequestDTO.getAccountId(),
                debitAccountRequestDTO.getCurrency(),
                debitAccountRequestDTO.getAmount()
        ));
    }

    @PostMapping("/credit")
    public CompletableFuture<String> placeDebit(@RequestBody CreditAccountRequestDTO creditAccountRequestDTO){
        return commandGateway.send(new CreateAccountCommand(
                creditAccountRequestDTO.getAccountId(),
                creditAccountRequestDTO.getCurrency(),
                creditAccountRequestDTO.getAmount()
        ));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
