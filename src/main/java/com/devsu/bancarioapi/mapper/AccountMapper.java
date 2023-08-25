package com.devsu.bancarioapi.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.devsu.bancarioapi.dtos.AccountResponseDto;
import com.devsu.bancarioapi.models.Account;
import com.devsu.bancarioapi.utilities.Format;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    /**
     * Converts an Account object to an AccountResponseDto.
     * 
     * @param acct
     * @return
     */
    public AccountResponseDto convertAccountToAccountDto(Account acct) {
        Format format = new Format();
        AccountResponseDto acctRespDto = new AccountResponseDto();
        acctRespDto.setAccount_id(acct.getAccountId());
        acctRespDto.setAccount_number(acct.getAccountNumber());
        String accType = (acct.getAccountType().toString().equals("savings_account")) ? "Ahorro" : "Corriente";
        acctRespDto.setAccount_type(accType);
        acctRespDto.setBalance(format.numberFormat(Double.parseDouble(acct.getBalance().toString())));
        acctRespDto.setStatus((acct.getStatus()) ? "True" : "False");
        acctRespDto.setCustomer_id(acct.getCustomer().getCustomerId());
        acctRespDto.setCustomer_name(acct.getCustomer().getPerson().getName());
        return acctRespDto;
    }

    /**
     * Converts a list of Account objects to a list of AccountResponseDto objects.
     * 
     * @param accts
     * @return
     */
    public List<AccountResponseDto> convertAccountsToAccountsDto(List<Account> accts) {
        return accts.stream().map(this::convertAccountToAccountDto).collect(Collectors.toList());
    }
}
