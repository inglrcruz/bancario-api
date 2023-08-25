package com.devsu.bancarioapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.devsu.bancarioapi.models.Movement;
import com.devsu.bancarioapi.repositories.MovementsRepository;

class MovementServiceTest {

    @Mock
    private MovementsRepository movementsRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private MovementService movementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Movement> expectedMovements = new ArrayList<>();
        expectedMovements.add(new Movement());
        expectedMovements.add(new Movement());
        when(movementsRepository.findAll()).thenReturn(expectedMovements);
        List<Movement> actualMovements = movementService.findAll();
        assertEquals(expectedMovements, actualMovements);
    }

    @Test
    void testFindById() {
        Movement expectedMovement = new Movement();
        when(movementsRepository.findById(1L)).thenReturn(Optional.of(expectedMovement));
        Movement actualMovement = movementService.findById(1L);
        assertEquals(expectedMovement, actualMovement);
    }
}