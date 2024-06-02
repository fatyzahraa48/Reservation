package com.zenika.example.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
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

import com.zenika.example.dao.jpa.RoomRepository;
import com.zenika.example.domain.Reservation;
import com.zenika.example.domain.Room;
import com.zenika.example.domain.RoomType;
import com.zenika.example.domain.TimeSlotEnum;
import com.zenika.example.exception.ResourceNotFoundException;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class findReserveAppropriateRoom {

    private static final Logger log = LoggerFactory.getLogger(findReserveAppropriateRoom.class);

    @Autowired
    private RoomRepository roomRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private  ReservationService reservationService;

    public findReserveAppropriateRoom() {
    }

    public Room findReserveAppropriateRoom(RoomType roomType,int capacity, LocalDate meetingDate, String startTime, String endTime, boolean reserve) throws Exception{
		Room appropriateRoom = null;

		try {
			// Execute the named query
			BigDecimal capacityDb = new BigDecimal(capacity).divide(new BigDecimal("0.70"), MathContext.DECIMAL128);
			List<Room> appropriateRooms = entityManager.createNamedQuery("Room.findAvailableRooms", Room.class)
					.setParameter("capacity", capacityDb.intValue()).setParameter("roomType", roomType).getResultList();
			List<String> availableSlots = null;
			log.info("availableRooms: {}",appropriateRooms.size());
			Reservation reservation=null;
			for (Room room : appropriateRooms) {

				availableSlots = getAvailableSlots(room, meetingDate, startTime, endTime);
				if (!availableSlots.isEmpty()) {
					appropriateRoom=room;
					if(reserve) {
						reservation=new Reservation(new Date(), room.getName().concat(availableSlots.get(0)), roomType, capacity, room, TimeSlotEnum.fromValue(availableSlots.get(0)));
						reservationService.createReservation(reservation);
					}
					
				}
			}

		} catch (Exception e) {
			log.error("findReserveAppropriateRoom error",e.getMessage());
			throw new Exception(e.getMessage());
		}
		log.info("availableRoom: {}",appropriateRoom!=null?appropriateRoom.getName():null);
		return appropriateRoom;
	}
    
    public static List<String> splitTimeRange(String startTime, String endTime) {
        List<String> timeSlots = new ArrayList<>();

        int startHour = Integer.parseInt(startTime);
        int endHour = Integer.parseInt(endTime);

        for (int hour = startHour; hour < endHour; hour++) {
            String timeSlot = String.format("%02d-%02d", hour, hour + 1);
            timeSlots.add(timeSlot);
        }

        return timeSlots;
    }
    
    private List<String> getAvailableSlots(Room room,LocalDate meetingDate, String startTime, String endTime) throws ResourceNotFoundException {
    	
    	if(startTime==null) {
    		startTime ="08";
    	}
    	if(endTime==null) {
    		endTime ="20";
    	}
    	 List<String> timeSlots = splitTimeRange(startTime, endTime);
    	 List<String> reservedHours = new ArrayList<>();
    	
    	List<Reservation> reservations = reservationService.findRoomReservationsByDate(room, meetingDate);
    	log.info("reservations: {}",reservations.size());
    	 for (Reservation reservation : reservations) {
    		 reservedHours.add(reservation.getTimeSlot().getRange());
    		 reservedHours.add(timeSlots.get(timeSlots.indexOf(reservation.getTimeSlot().getRange())+1));
         }
    	 log.info("reservedHours: {}",reservedHours.size());
    	 
    	
    	 
    	 timeSlots.removeAll(reservedHours);
    	 
    	 
    	 log.info("timeSlots: {}",timeSlots.size());
    	 
        return timeSlots;
    }
    

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoom(long id) {
        return roomRepository.findOne(id);
    }

    public void updateRoom(Room room) {
    	roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
    	roomRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<Room> getAllRooms(Integer page, Integer size) {
        Page pageOfRomms = roomRepository.findAll(new PageRequest(page, size));

        return pageOfRomms;
    }

}
