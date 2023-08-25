package com.devsu.bancarioapi.controllers;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.bancarioapi.dtos.MovementReportResponseDto;
import com.devsu.bancarioapi.exceptionHandler.ErrorResponse;
import com.devsu.bancarioapi.mapper.MovementMapper;
import com.devsu.bancarioapi.repositories.MovementsRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final MovementsRepository movRep;

    /**
     * Return customer by id
     * 
     * @return
     */
    @GetMapping("/account-status")
    public ResponseEntity<?> getMovements(@RequestParam(name = "from") String fromDate,
            @RequestParam(name = "to") String toDate) {
        try {
            if (fromDate.isEmpty())
                throw new NoSuchElementException("El parametro from es requerido");
            if (toDate.isEmpty())
                throw new NoSuchElementException("El parametro to es requerido");
            List<Object[]> results = movRep.getMovementDetails(fromDate, toDate);
            List<MovementReportResponseDto> movtsRepDtos = new MovementMapper()
                    .convertMovementsReportToMovementsReportDto(results);
            return ResponseEntity.ok(movtsRepDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage()));
        }
    }

}