package com.devsu.bancarioapi.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.devsu.bancarioapi.dtos.AccountDto;
import com.devsu.bancarioapi.models.Account;
import com.devsu.bancarioapi.models.Customer;
import com.devsu.bancarioapi.repositories.AccountsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountsRepository acctRepo;
    private final CustomerService custSvr;

    /**
     * Add new account
     * 
     * @param cust
     * @return
     */
    public Long create(AccountDto acctDto) {
        Customer cust = this.custSvr.findById(acctDto.getCustomer_id());
        if (cust == null)
            throw new RuntimeException("El cliente no existe");
        if (this.acctRepo.findByAccountNumber(acctDto.getAccount_number()) != null)
            throw new RuntimeException("Ya existe un cliente con este número de cuenta.");
        Account acct = new Account();
        acct.setAccountNumber(acctDto.getAccount_number());
        acct.setAccountType(acctDto.getAccount_type());
        acct.setBalance(acctDto.getBalance());
        acct.setCustomer(cust);
        return acctRepo.save(acct).getAccountId();
    }

    /**
     * Get list of accounts
     * 
     * @return
     */
    public List<Account> findAll() {
        return acctRepo.findAll();
    }

    /**
     * Get account by id
     * 
     * @return
     */
    public Account findById(Long id) {
        return acctRepo.findById(id).orElse(null);
    }

    /**
     * Update customer by id
     * 
     * @param request
     * @return
     */
    public Account update(Long id, AccountDto acctDto) {
        Account exisAcct = this.findById(id);
        if (exisAcct != null) {
            if (acctDto.getAccount_number() != null && exisAcct.getAccountNumber() != acctDto.getAccount_number())
                exisAcct.setAccountNumber(acctDto.getAccount_number());
            if (acctDto.getAccount_type() != null)
                exisAcct.setAccountType(acctDto.getAccount_type());
            if (acctDto.getBalance() != null)
                exisAcct.setBalance(acctDto.getBalance());
            return acctRepo.save(exisAcct);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Delete account by id
     * 
     * @param id
     */
    public void delete(Long id) {
        acctRepo.deleteById(id);
    }
}