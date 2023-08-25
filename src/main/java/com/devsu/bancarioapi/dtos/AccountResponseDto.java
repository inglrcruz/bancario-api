package com.devsu.bancarioapi.dtos;

import lombok.Data;

@Data
public class AccountResponseDto {

    private Long account_id;
    private Integer account_number;
    private String account_type;
    private String balance;
    private Long customer_id;
    private String customer_name;
    private String status;

}