package com.bank.electronic.query.dto;

import com.bank.electronic.commonapi.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountWatchEvent {
    private Instant instant;
    private String accountId;
    private double currentBalance;
    private TransactionType type;
    private double transactionAmount;
}
