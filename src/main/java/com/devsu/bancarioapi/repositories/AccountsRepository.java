package com.devsu.bancarioapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devsu.bancarioapi.models.Account;

public interface AccountsRepository extends JpaRepository<Account, Number> {

    Account findByAccountNumber(Integer accountNumber);
}