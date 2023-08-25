package com.devsu.bancarioapi.dtos;

import lombok.Data;

@Data
public class CustomerResponseDto {

    private Long customer_id;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String password;
    private String address;
    private String phone;
    private String status;

}