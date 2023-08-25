package com.devsu.bancarioapi.dtos;

import com.devsu.bancarioapi.enums.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MovementDto {

    @NotNull(message = "La cuenta no puede estar en blanco")
    @Positive(message = "La cuenta debe ser un valor positivo")
    private Long account_id;

    @NotNull(message = "El tipo de movimiento no puede estar en blanco")
    private MovementType movement_type;

    @Positive(message = "El valor debe ser un valor positivo")
    private Double value;

}