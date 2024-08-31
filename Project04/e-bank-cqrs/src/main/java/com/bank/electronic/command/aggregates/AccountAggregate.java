package com.bank.electronic.command.aggregates;

import com.bank.electronic.commonapi.commands.CreateAccountCommand;
import com.bank.electronic.commonapi.commands.CreditAccountCommand;
import com.bank.electronic.commonapi.commands.DebitAccountCommand;
import com.bank.electronic.commonapi.enums.AccountStatus;
import com.bank.electronic.commonapi.events.AccountCreatedEvent;
import com.bank.electronic.commonapi.events.AccountCreditEvent;
import com.bank.electronic.commonapi.events.AccountDebitEvent;
import com.bank.electronic.commonapi.exceptions.NegativeInitialBalanceException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
@NoArgsConstructor //necessary empty constructor
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private String currency;
    private double balance;
    private AccountStatus accountStatus;

    @CommandHandler
    public AccountAggregate (CreateAccountCommand createAccountCommand){
        log.info("CreateAccountCommand received");
        if (createAccountCommand.getInitialBalance() < 0){
            throw new NegativeInitialBalanceException("Error, it is not possible to have a negative balance");
        }
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getCurrency(),
                createAccountCommand.getInitialBalance(),
                AccountStatus.CREATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("Event - AccountCreateEvent");
        this.accountId=accountCreatedEvent.getId();
        this.currency=accountCreatedEvent.getCurrency();
        this.balance= accountCreatedEvent.getBalance();
        this.accountStatus=accountCreatedEvent.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){
        log.info("CreditAccountCommand received");
        if (creditAccountCommand.getAmount()<0)
            throw new NegativeInitialBalanceException("Error, it is not possible to have a negative balance.");
        AggregateLifecycle.apply(new AccountCreditEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getCurrency(),
                creditAccountCommand.getAmount()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditEvent accountCreditEvent){
        log.info("Event - AccountCreditEvent");
        this.balance+= accountCreditEvent.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand){
        log.info("DebitAccountCommand received");
        if (debitAccountCommand.getAmount() < 0)
            throw new NegativeInitialBalanceException("Error, it is not possible to have a negative balance.");
        if (debitAccountCommand.getAmount() > this.balance)
            throw  new RuntimeException("Insufficient balance");
        AggregateLifecycle.apply(new AccountDebitEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getCurrency(),
                debitAccountCommand.getAmount()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitEvent accountDebitEvent){
        log.info("Event - AccountCreditEvent");
        this.balance-= accountDebitEvent.getAmount();
    }

}
