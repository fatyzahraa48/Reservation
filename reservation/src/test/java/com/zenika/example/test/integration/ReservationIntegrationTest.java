package com.zenika.example.api.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllReservations() throws Exception {
        // Arrange (No specific arrangement needed for this test)

        // Act
        ResultActions resultActions = mockMvc.perform(get("/example/v1/reservations"));

        // Assert
        resultActions.andExpect(status().isOk());
    }

    // Add more integration test cases as needed
}
