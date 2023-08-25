package com.devsu.bancarioapi.dtos;

import lombok.Data;

@Data
public class MovementResponseDto {

    private Long movement_id;
    private Integer account_number;
    private String account_type;
    private String type;
    private String motion;
    private String initial_balance;
    private String balance;
    private String date;
    private String status;

}