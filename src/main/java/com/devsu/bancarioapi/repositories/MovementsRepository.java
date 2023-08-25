package com.devsu.bancarioapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.devsu.bancarioapi.models.Movement;
import java.util.List;

public interface MovementsRepository extends JpaRepository<Movement, Long> {

    @Query(value = " Select date_format(m.date, '%d/%m/%Y') as 'fecha', p.name as 'cliente', cast(a.account_number as CHAR) as 'numeroCuenta', "+
            "    if(a.account_type = 'savings_account', 'Ahorro', 'Corriente') as 'tipo', m.initial_balance as 'saldoInicial', "+
            "    if(a.status, 'True', 'False') as 'estado', if(m.movement_type ='debit', m.value*-1, m.value) as 'movimiento', "+
            "    m.balance as 'saldoDisponible' " +
            " from movements m " +
            "    inner join accounts a on a.account_id=m.account_id " +
            "    inner join customers c on c.customer_id=a.customer_id " +
            "    inner join persons p on p.person_id=c.person_id  " +
            " where m.date between :fromDate and :toDate ", nativeQuery = true)
    List<Object[]> getMovementDetails(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "Select sum(m.value) from movements m where m.account_id = :accountId", nativeQuery = true)
    Double sumByAccountId(@Param("accountId") Long accountId);

}