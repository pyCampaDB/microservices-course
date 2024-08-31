package com.bank.electronic.query.controllers;

import com.bank.electronic.query.entities.Account;
import com.bank.electronic.query.queries.GetAccountById;
import com.bank.electronic.query.queries.GetAllAccounts;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/account")
public class AccountQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/list")
    public CompletableFuture<List<Account>> accountsList(){
        return queryGateway.query(
                new GetAllAccounts(),
                ResponseTypes.multipleInstancesOf(Account.class)
        );
    }

    @GetMapping("/byId/{id}")
    public CompletableFuture<Account> getAccountById (@PathVariable String id){
        return queryGateway.query(
            new GetAccountById(id),
            ResponseTypes.instanceOf(Account.class)
        );
    }

    /*@GetMapping("{")
    public Flux<AccountWatchEvent> accountBalance(@PathVariable String accountId){

    }*/
}
