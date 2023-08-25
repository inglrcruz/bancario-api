package com.devsu.bancarioapi.controllers;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.bancarioapi.dtos.MovementDto;
import com.devsu.bancarioapi.dtos.MovementResponseDto;
import com.devsu.bancarioapi.exceptionHandler.ErrorResponse;
import com.devsu.bancarioapi.mapper.MovementMapper;
import com.devsu.bancarioapi.models.Movement;
import com.devsu.bancarioapi.services.MovementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movServ;
    private final HttpServletRequest request;

    /**
     * Create new movement
     * 
     * @param movDto
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> save(@Valid @RequestBody MovementDto movDto) {
        try {
            Long id = movServ.create(movDto);
            String url = request.getRequestURI() + "show/";
            return ResponseEntity.created(URI.create(url + id)).body("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage()));
        }
    }

    /**
     * Return list of movements
     * 
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<MovementResponseDto>> findAll() {
        List<Movement> movts = movServ.findAll();
        List<MovementResponseDto> custsRepDtos = new MovementMapper().convertMovementsToMovementsDto(movts);
        return ResponseEntity.ok(custsRepDtos);
    }

    /**
     * Return movement by id
     * 
     * @return
     */
    @GetMapping("/show/{id}")
    public ResponseEntity<MovementResponseDto> findById(@PathVariable Long id) {
        Movement movt = movServ.findById(id);
        if (movt != null) {
            MovementResponseDto movtRepDto = new MovementMapper().convertMovementToMovementDto(movt);
            return ResponseEntity.ok(movtRepDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete movement by id
     * 
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Movement mov = movServ.findById(id);
        if (mov != null) {
            movServ.delete(id, mov);
            return ResponseEntity.ok().body("");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
