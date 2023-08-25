package com.devsu.bancarioapi.mapper;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.devsu.bancarioapi.dtos.MovementReportResponseDto;
import com.devsu.bancarioapi.dtos.MovementResponseDto;
import com.devsu.bancarioapi.models.Movement;
import com.devsu.bancarioapi.utilities.Format;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovementMapper {

    /**
     * Converts a Movement object to a MovementResponseDto.
     * 
     * @param movt
     * @return
     */
    public MovementResponseDto convertMovementToMovementDto(Movement movt) {
        Format format = new Format();
        MovementResponseDto movRespDto = new MovementResponseDto();
        movRespDto.setMovement_id(movt.getMovementId());
        movRespDto.setAccount_number(movt.getAccount().getAccountNumber());
        movRespDto.setAccount_type(
                (movt.getAccount().getAccountType().toString().equals("savings_account")) ? "Ahorro" : "Corriente");
        movRespDto.setType((movt.getMovementType().toString().equals("credit")) ? "Crédito" : "Débito");
        String movtType = movt.getMovementType().toString();
        movRespDto.setInitial_balance(format.numberFormat(movt.getInitialBalance()));
        movRespDto.setBalance(format.numberFormat(movt.getBalance()));
        movRespDto.setMotion(((movtType.equals("credit")) ? "Deposito de " : "Retiro de ")
                + format.numberFormat(movt.getValue()));
        movRespDto.setDate(format.dateFormat(movt.getDate(), "dd/MM/yyyy"));
        movRespDto.setStatus((movt.getAccount().getStatus()) ? "True" : "False");
        return movRespDto;
    }

    /**
     * Converts a list of Movement objects to a list of MovementResponseDto objects.
     * 
     * @param movt
     * @return
     */
    public List<MovementResponseDto> convertMovementsToMovementsDto(List<Movement> movt) {
        return movt.stream().map(this::convertMovementToMovementDto).collect(Collectors.toList());
    }

    /**
     * Converts a list of query results to a list of MovementReportResponseDto
     * objects.
     * 
     * @param results
     * @return
     */
    public List<MovementReportResponseDto> convertMovementsReportToMovementsReportDto(List<Object[]> results) {
        Format format = new Format();
        List<MovementReportResponseDto> movtDtsRep = new ArrayList<>();
        for (Object[] result : results) {
            MovementReportResponseDto movRepRespDto = new MovementReportResponseDto();
            movRepRespDto.setDate(result[0].toString());
            movRepRespDto.setCustomer_name(result[1].toString());
            movRepRespDto.setAccount_number(result[2].toString());
            movRepRespDto.setAccount_type(result[3].toString());
            movRepRespDto.setInitial_balance(format.numberFormat(Double.parseDouble(result[4].toString())));
            movRepRespDto.setStatus(result[5].toString());
            String motion = ((Double.parseDouble(result[6].toString()) > 0) ? "Deposito de " : "Retiro de ")
                    + format.numberFormat(Double.parseDouble(result[6].toString()));
            movRepRespDto.setMotion(motion);
            movRepRespDto.setBalance(format.numberFormat(Double.parseDouble(result[7].toString())));
            movtDtsRep.add(movRepRespDto);
        }
        return movtDtsRep;
    }

}