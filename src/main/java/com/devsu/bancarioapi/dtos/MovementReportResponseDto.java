package com.devsu.bancarioapi.dtos;

import lombok.Data;

@Data
public class MovementReportResponseDto {

  private String date;
  private String customer_name;
  private String account_number;
  private String account_type;
  private String initial_balance;
  private String status;
  private String motion;
  private String balance;
  
}