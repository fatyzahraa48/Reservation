package com.zenika.example.test.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class ReservationTest {

    @Test
    public void testReservationConstructorAndGetters() {
        // Arrange
        Date date = new Date();
        String meetingCode = "Meeting123";
        RoomType type = RoomType.CONFERENCE_ROOM;
        int numberOfPeople = 10;
        Room room = new Room();
        TimeSlotEnum timeSlot = TimeSlotEnum.MORNING;

        // Act
        Reservation reservation = new Reservation(date, meetingCode, type, numberOfPeople, room, timeSlot);

        // Assert
        assertNotNull(reservation);
        assertEquals(date, reservation.getDate());
        assertEquals(meetingCode, reservation.getMeetingCode());
        assertEquals(type, reservation.getType());
        assertEquals(numberOfPeople, reservation.getNumberOfPeople());
        assertEquals(room, reservation.getRoom());
        assertEquals(timeSlot, reservation.getTimeSlot());
    }

    // Add more test cases as needed
}
