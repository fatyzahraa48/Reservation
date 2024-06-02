package com.zenika.example.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
@NamedQuery(
	    name = "Reservation.findReservationByDate",
	    query = "SELECT r FROM Reservation r " +
	    		"WHERE r.room.id = :roomId " +
	    		"AND r.date=:meetingDate"
	)
})
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String meetingCode;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private int participantsNumber;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    
    @Column(name = "time_slot")
    private TimeSlotEnum timeSlot;

    public Reservation() {
    }

 

    public Reservation(Date date, String meetingCode, RoomType type, int participantsNumber, Room room,
			TimeSlotEnum timeSlot) {
		super();
		this.date = date;
		this.meetingCode = meetingCode;
		this.type = type;
		this.participantsNumber = participantsNumber;
		this.room = room;
		this.timeSlot = timeSlot;
	}



	// Getters et setters

    public Date getDate() {
        return date;
    }



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getMeetingCode() {
		return meetingCode;
	}



	public void setMeetingCode(String meetingCode) {
		this.meetingCode = meetingCode;
	}



	public RoomType getType() {
		return type;
	}



	public void setType(RoomType type) {
		this.type = type;
	}



	public int getParticipantsNumber() {
		return participantsNumber;
	}



	public void setParticipantsNumber(int participantsNumber) {
		this.participantsNumber = participantsNumber;
	}



	public Room getRoom() {
		return room;
	}



	public void setRoom(Room room) {
		this.room = room;
	}



	public TimeSlotEnum getTimeSlot() {
		return timeSlot;
	}



	public void setTimeSlot(TimeSlotEnum timeSlot) {
		this.timeSlot = timeSlot;
	}



	public void setDate(Date date) {
		this.date = date;
	}

      
}