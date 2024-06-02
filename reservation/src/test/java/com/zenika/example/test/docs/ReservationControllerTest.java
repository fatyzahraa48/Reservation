package com.zenika.example.test.docs;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenika.example.domain.Reservation;
import com.zenika.example.domain.Room;
import com.zenika.example.domain.RoomType;
import com.zenika.example.service.ReservationService;
import com.zenika.example.service.findReserveAppropriateRoom;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private findReserveAppropriateRoom roomService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindReserveAppropriateRoom() throws Exception {
        // Prepare mock data
        Room room = new Room();
        room.setId(1L);
        room.setName("Meeting Room 1");
        room.setType(RoomType.SMALL);
        room.setCapacity(10);

        LocalDate meetingDate = LocalDate.of(2024, 6, 15);

        when(roomService.findReserveAppropriateRoom(RoomType.SMALL, 10, meetingDate, "09:00", "10:00", true))
                .thenReturn(room);

        // Perform GET request
        ResultActions resultActions = mockMvc.perform(get("/example/v1/reservations/findReserveAvailableRoom")
                .param("roomType", "SMALL")
                .param("capacity", "10")
                .param("meetingDate", "2024-06-15")
                .param("startTime", "09:00")
                .param("endTime", "10:00")
                .param("reserve", "true"));

        // Verify response
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Meeting Room 1"))
                .andExpect(jsonPath("$.type").value("SMALL"))
                .andExpect(jsonPath("$.capacity").value(10));
    }

    // Add more test cases for other methods in ReservationController
}
