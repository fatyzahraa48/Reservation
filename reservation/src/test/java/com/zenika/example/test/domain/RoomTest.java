package com.zenika.example.test.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class RoomTest {

    @Test
    public void testRoomConstructorAndGetters() {
        // Arrange
        String name = "Room1";
        int capacity = 10;
        RoomType type = RoomType.CONFERENCE_ROOM;

        // Act
        Room room = new Room(name, capacity, type);

        // Assert
        assertNotNull(room);
        assertEquals(name, room.getName());
        assertEquals(capacity, room.getCapacity());
        assertEquals(type, room.getType());
    }

    // Add more test cases as needed
}

