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
import com.devsu.bancarioapi.dtos.CustomerDto;
import com.devsu.bancarioapi.dtos.CustomerResponseDto;
import com.devsu.bancarioapi.exceptionHandler.ErrorResponse;
import com.devsu.bancarioapi.mapper.CustomerMapper;
import com.devsu.bancarioapi.models.Customer;
import com.devsu.bancarioapi.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService custServ;
    private final HttpServletRequest request;

    /**
     * Create new customer
     * 
     * @param custDto
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> save(@Valid @RequestBody CustomerDto custDto) {
        try {
            Long id = custServ.create(custDto);
            String url = request.getRequestURI() + "show/";
            log.info("New client added ID: " + id + " Name: " + custDto.getName());
            return ResponseEntity.created(URI.create(url + id)).body("");
        } catch (Exception e) {
            log.error("Error adding client", e.getMessage());
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            e.getMessage()));
        }
    }

    /**
     * Return list of customers
     * 
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<CustomerResponseDto>> findAll() {
        List<Customer> custs = custServ.findAll();
        List<CustomerResponseDto> custsRepDtos = new CustomerMapper().convertCustomersToCustomersDto(custs);
        return ResponseEntity.ok(custsRepDtos);
    }

    /**
     * Return customer by id
     * 
     * @return
     */
    @GetMapping("/show/{id}")
    public ResponseEntity<CustomerResponseDto> findById(@PathVariable Long id) {
        Customer cust = custServ.findById(id);
        if (cust != null) {
            CustomerResponseDto custRepDto = new CustomerMapper().convertCustomerToCustomerDto(cust);
            return ResponseEntity.ok(custRepDto);
        } else {
            log.error("client id not found" + id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update mother by id
     * 
     * @param id
     * @param body
     * @param httpRequest
     * @return
     */
    @PatchMapping("/update/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody CustomerDto custDto) {
        try {
            Customer custUpt = custServ.update(id, custDto);
            log.info("Client updated successfully");
            return ResponseEntity.ok(custUpt);
        } catch (NoSuchElementException e) {
            log.error("Error editing client", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete customer by id
     * 
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Client successfully removed");
        custServ.delete(id);
    }
}