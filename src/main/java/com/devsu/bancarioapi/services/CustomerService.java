package com.devsu.bancarioapi.services;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import com.devsu.bancarioapi.dtos.CustomerDto;
import com.devsu.bancarioapi.models.Customer;
import com.devsu.bancarioapi.models.Person;
import com.devsu.bancarioapi.repositories.CustomersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomersRepository custRepo;
    private final PersonService perSev;

    /**
     * Add new customer
     * 
     * @param cust
     * @return
     */
    public Long create(CustomerDto custDto) {
        if (perSev.findByIdentification(custDto.getIdentification()) != null)
            throw new RuntimeException("Ya existe un cliente con esta identificación.");
        Customer cust = new Customer();
        cust.setPassword(custDto.getPassword());
        cust.setPerson(perSev.create(custDto));
        return custRepo.save(cust).getCustomerId();
    }

    /**
     * Get list of customers
     * 
     * @return
     */
    public List<Customer> findAll() {
        return custRepo.findAll();
    }

    /**
     * Get customer by id
     * 
     * @return
     */
    public Customer findById(Long id) {
        return custRepo.findById(id).orElse(null);
    }

    /**
     * Update customer by id
     * 
     * @param request
     * @return
     */
    public Customer update(Long id, CustomerDto custDto) {
        Customer exisCust = this.findById(id);
        if (exisCust != null) {
            if (custDto.getPassword() != null)
                exisCust.setPassword(custDto.getPassword());
            Person prs = perSev.update(id, custDto);
            if (prs != null)
                exisCust.setPerson(prs);
            return custRepo.save(exisCust);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Delete customer by id
     * 
     * @param id
     */
    public void delete(Long id) {
        custRepo.deleteById(id);
    }

}