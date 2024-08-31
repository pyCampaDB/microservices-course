package com.bank.electronic.query.entities;

import com.bank.electronic.commonapi.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    private String id;
    private Instant createdAt;
    private double balance;
    private AccountStatus status;
    private String currency;
    @OneToMany(mappedBy = "account")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<AccountTransaction> transactions;

}
