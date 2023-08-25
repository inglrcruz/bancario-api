package com.devsu.bancarioapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devsu.bancarioapi.models.Customer;

public interface CustomersRepository extends JpaRepository<Customer, Long> { }