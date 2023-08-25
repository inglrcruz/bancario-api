package com.devsu.bancarioapi.controllers;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.bancarioapi.dtos.AccountDto;
import com.devsu.bancarioapi.dtos.AccountResponseDto;
import com.devsu.bancarioapi.exceptionHandler.ErrorResponse;
import com.devsu.bancarioapi.mapper.AccountMapper;
import com.devsu.bancarioapi.models.Account;
import com.devsu.bancarioapi.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService acctServ;
    private final HttpServletRequest request;

    /**
     * Create new account
     * 
     * @param cust
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> save(@Valid @RequestBody AccountDto acctDto) {
        try {
            Long id = acctServ.create(acctDto);
            String url = request.getRequestURI() + "show/";
            return ResponseEntity.created(URI.create(url + id)).body("");
        } catch (Exception e) {
            log.error("Error creating account, Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            e.getMessage()));
        }
    }

    /**
     * Return list of accounts
     * 
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<AccountResponseDto>> findAll() {
        List<Account> accts = acctServ.findAll();
        List<AccountResponseDto> acctsRepDtos = new AccountMapper().convertAccountsToAccountsDto(accts);
        return ResponseEntity.ok(acctsRepDtos);
    }

    /**
     * Return account by id
     * 
     * @return
     */
    @GetMapping("/show/{id}")
    public ResponseEntity<AccountResponseDto> findById(@PathVariable Long id) {
        Account acct = acctServ.findById(id);
        if (acct != null) {
            AccountResponseDto acctRepDto = new AccountMapper().convertAccountToAccountDto(acct);
            return ResponseEntity.ok(acctRepDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update account by id
     * 
     * @param id
     * @param body
     * @param httpRequest
     * @return
     */
    @PatchMapping("/update/{id}")
    public ResponseEntity<Account> update(@PathVariable Long id, @RequestBody AccountDto acctDto) {
        try {
            log.info("Account updated successfully");
            return ResponseEntity.ok(acctServ.update(id, acctDto));
        } catch (NoSuchElementException e) {
            log.error("Error editing account", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete account by id
     * 
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Account successfully removed");
        acctServ.delete(id);
    }
}
