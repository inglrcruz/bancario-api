package com.devsu.bancarioapi.services;

import org.springframework.stereotype.Service;
import com.devsu.bancarioapi.dtos.CustomerDto;
import com.devsu.bancarioapi.models.Person;
import com.devsu.bancarioapi.repositories.PersonsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonsRepository perRepo;

    /**
     * Add new person
     * 
     * @param cust
     * @return
     */
    public Person create(CustomerDto custDto) {
        Person p = new Person();
        p.setAge(custDto.getAge());
        p.setGender(custDto.getGender());
        p.setAddress(custDto.getAddress());
        p.setName(custDto.getName());
        p.setPhone(custDto.getPhone());
        p.setIdentification(custDto.getIdentification());
        return perRepo.save(p);
    }

    /**
     * Get person by id
     * 
     * @return
     */
    public Person findById(Long id) {
        return perRepo.findById(id).orElse(null);
    }

    /**
     * Find person by id
     * 
     * @param id
     * @return
     */
    public Person findByIdentification(String id) {
        return perRepo.findByIdentification(id);
    }

    /**
     * Update person by id
     * 
     * @param request
     * @return
     */
    public Person update(Long id, CustomerDto custDto) {
        Person exisPers = findById(id);
        if (custDto.getAge() != null)
            exisPers.setAge(custDto.getAge());
        if (custDto.getGender() != null)
            exisPers.setGender(custDto.getGender());
        if (custDto.getAddress() != null)
            exisPers.setAddress(custDto.getAddress());
        if (custDto.getName() != null)
            exisPers.setName(custDto.getName());
        if (custDto.getPhone() != null)
            exisPers.setPhone(custDto.getPhone());
        if (custDto.getIdentification() != null && custDto.getIdentification() != exisPers.getIdentification())
            exisPers.setIdentification(custDto.getIdentification());
        return perRepo.save(exisPers);
    }

}
