package com.zenika.example.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zenika.example.dao.jpa.ReservationRepository;
import com.zenika.example.domain.Reservation;
import com.zenika.example.domain.Room;
import com.zenika.example.exception.ResourceNotFoundException;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    public ReservationService() {
    }

    public List<Reservation> findRoomReservationsByDate(Room room,LocalDate meetingDate) throws ResourceNotFoundException {
    	
    	if(room==null) {
    		throw new ResourceNotFoundException("room is null");
    	}
    	Date date=Date.from(meetingDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Reservation> reservations = entityManager.createNamedQuery("Reservation.findReservationByDate", Reservation.class)
                .setParameter("roomId", room.getId())
                .setParameter("meetingDate", date)
                .getResultList();
        
        return reservations;
    }

    public Reservation createReservation(Reservation room) {
        return reservationRepository.save(room);
    }

    public Reservation getReservation(long id) {
        return reservationRepository.findOne(id);
    }

    public void updateReservation(Reservation room) {
    	reservationRepository.save(room);
    }

    public void deleteReservation(Long id) {
    	reservationRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<Reservation> getAllReservations(Integer page, Integer size) {
        Page pageOfReservations = reservationRepository.findAll(new PageRequest(page, size));

        return pageOfReservations;
    }

}
