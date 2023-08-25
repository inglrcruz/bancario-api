package com.devsu.bancarioapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devsu.bancarioapi.models.Person;

public interface PersonsRepository extends JpaRepository<Person, Long> {

    Person findByIdentification(String identification);
}