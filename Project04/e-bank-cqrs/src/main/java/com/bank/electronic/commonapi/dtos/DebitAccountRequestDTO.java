package com.bank.electronic.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebitAccountRequestDTO {
    private String accountId;
    private String currency;
    private double amount;
}
