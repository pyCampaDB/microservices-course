package com.bank.electronic.commonapi.events;

import com.bank.electronic.commonapi.enums.AccountStatus;
import lombok.Getter;

@Getter
public class AccountCreatedEvent extends BaseEvent<String>{
    private String currency;
    private double balance;
    private AccountStatus status;

    public AccountCreatedEvent(String id, String currency, double balance, AccountStatus status) {
        super(id);
        this.currency = currency;
        this.balance = balance;
        this.status = status;
    }
}
