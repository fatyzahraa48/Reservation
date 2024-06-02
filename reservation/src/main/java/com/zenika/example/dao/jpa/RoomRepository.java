package com.zenika.example.dao.jpa;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zenika.example.domain.Room;
import com.zenika.example.domain.RoomType;
import com.zenika.example.domain.TimeSlotEnum;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
   
}
