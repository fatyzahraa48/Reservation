package com.zenika.example.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zenika.example.domain.Reservation;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface ReservationRepository extends PagingAndSortingRepository<Reservation, Long> {
   
}
