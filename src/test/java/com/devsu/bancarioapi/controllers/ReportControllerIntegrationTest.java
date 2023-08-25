package com.devsu.bancarioapi.controllers;

import com.devsu.bancarioapi.repositories.MovementsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
@AutoConfigureMockMvc
public class ReportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovementsRepository movementsRepository;

    @Test
    public void testGetMovements() throws Exception {
        mockMvc.perform(get("/v1/reports/account-status")
                .param("from", "2023-01-01")
                .param("to", "2023-12-31")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
