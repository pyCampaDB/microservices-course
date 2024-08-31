package com.bank.electronic.query.repositories;

import com.bank.electronic.query.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<AccountTransaction, Long> {
    AccountTransaction findTop1ByAccountIdOrderByTimestampDesc(String accountId);
}
