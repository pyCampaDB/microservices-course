package com.bank.electronic.query.repositories;

import com.bank.electronic.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
