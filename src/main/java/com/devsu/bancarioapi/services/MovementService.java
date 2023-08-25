package com.devsu.bancarioapi.services;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import com.devsu.bancarioapi.dtos.AccountDto;
import com.devsu.bancarioapi.dtos.MovementDto;
import com.devsu.bancarioapi.enums.MovementType;
import com.devsu.bancarioapi.models.Account;
import com.devsu.bancarioapi.models.Movement;
import com.devsu.bancarioapi.repositories.MovementsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementsRepository movRepo;
    private final AccountService acctServ;

    /**
     * Add new movement
     * 
     * @param cust
     * @return
     */
    public Long create(MovementDto movDto) {
        Account acct = this.acctServ.findById(movDto.getAccount_id());
        Movement mv = new Movement();
        mv.setAccount(acct);
        mv.setMovementType(movDto.getMovement_type());
        mv.setValue(movDto.getValue());
        mv.setInitialBalance(acct.getBalance());
        Double balance = 0.0;
        if (movDto.getMovement_type().toString().equals("credit")) {
            balance = acct.getBalance() + movDto.getValue();
        } else {
            Double sum = movRepo.sumByAccountId(movDto.getAccount_id());
            if(sum !=null && sum >= 1000) throw new NoSuchElementException("Cupo diario Excedido");
            if (acct.getBalance() == 0) throw new NoSuchElementException("Saldo no disponible");
            balance = acct.getBalance() - movDto.getValue();
        }
        mv.setBalance(balance);
        // Update balance
        AccountDto acctDto = new AccountDto();
        acctDto.setBalance(balance);
        this.acctServ.update(acct.getAccountId(), acctDto);
        // Add movement
        return movRepo.save(mv).getMovementId();
    }

    /**
     * Get list of movements
     * 
     * @return
     */
    public List<Movement> findAll() {
        return movRepo.findAll();
    }

    /**
     * Get movement by id
     * 
     * @return
     */
    public Movement findById(Long id) {
        return movRepo.findById(id).orElse(null);
    }

    /**
     * Delete movement by id
     * 
     * @param id
     */
    public void delete(Long id, Movement mv) {
        Long acctId = mv.getAccount().getAccountId();
        Account acct = this.acctServ.findById(acctId);
        // Update balance
        Double balance = 0.0;
        if (mv.getMovementType().equals(MovementType.debit)) {
            balance = acct.getBalance() + mv.getValue();
        } else {
            balance = acct.getBalance() - mv.getValue();
        }
        AccountDto acctDto = new AccountDto();
        acctDto.setBalance(balance);
        this.acctServ.update(acct.getAccountId(), acctDto);
        // Delete row
        movRepo.deleteById(id);
    }
}
