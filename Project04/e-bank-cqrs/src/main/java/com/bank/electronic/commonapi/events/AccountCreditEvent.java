package com.bank.electronic.commonapi.events;

import lombok.Getter;

@Getter
public class AccountCreditEvent extends BaseEvent<String>{
    private String currency;
    private double amount;

    public AccountCreditEvent(String id, String currency, double amount) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
