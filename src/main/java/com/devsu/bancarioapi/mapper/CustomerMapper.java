package com.devsu.bancarioapi.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.devsu.bancarioapi.dtos.CustomerResponseDto;
import com.devsu.bancarioapi.models.Customer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    /**
     * Converts a Customer object to a CustomerResponseDto object.
     * 
     * @param cust
     * @return
     */
    public CustomerResponseDto convertCustomerToCustomerDto(Customer cust) {
        CustomerResponseDto custRespDto = new CustomerResponseDto();
        custRespDto.setCustomer_id(cust.getCustomerId());
        custRespDto.setName(cust.getPerson().getName());
        custRespDto.setGender(cust.getPerson().getGender().toString());
        custRespDto.setAge(cust.getPerson().getAge());
        custRespDto.setIdentification(cust.getPerson().getIdentification());
        custRespDto.setPassword(cust.getPassword());
        custRespDto.setAddress(cust.getPerson().getAddress());
        custRespDto.setPhone(cust.getPerson().getPhone());
        custRespDto.setStatus((cust.getStatus()) ? "True" : "False");
        return custRespDto;
    }

    /**
     * Converts a list of Customer objects to a list of CustomerResponseDto objects.
     * 
     * @param custs
     * @return
     */
    public List<CustomerResponseDto> convertCustomersToCustomersDto(List<Customer> custs) {
        return custs.stream().map(this::convertCustomerToCustomerDto).collect(Collectors.toList());
    }

}
