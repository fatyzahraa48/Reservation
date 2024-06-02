package com.zenika.example.api.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zenika.example.domain.Reservation;
import com.zenika.example.domain.Room;
import com.zenika.example.domain.RoomType;
import com.zenika.example.exception.DataFormatException;
import com.zenika.example.service.ReservationService;
import com.zenika.example.service.findReserveAppropriateRoom;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/example/v1/reservations")
@Api(tags = {"reservations"})
public class ReservationController extends AbstractRestHandler {

    @Autowired
    private findReserveAppropriateRoom roomService;
    
    @Autowired
    private ReservationService reservationService;
    

    @GetMapping("/findReserveAvailableRoom")
    public ResponseEntity<Room> findReserveAppropriateRoom(
            @RequestParam RoomType roomType,
            @RequestParam int capacity,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate meetingDate,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam boolean reserve
            
    ) {
    	Room response=null;
		try {
			response = roomService.findReserveAppropriateRoom(roomType, capacity, meetingDate, startTime, endTime,reserve);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/room",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a room resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createRoom(@RequestBody Room room,
                                 HttpServletRequest request, HttpServletResponse response) {
        Room createdRoom = this.roomService.createRoom(room);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdRoom.getId()).toString());
    }
    
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a room resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createReservation(@RequestBody Reservation reservation,
                                 HttpServletRequest request, HttpServletResponse response) {
        Reservation createdReservation = this.reservationService.createReservation(reservation);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdReservation.getId()).toString());
    }

    @RequestMapping(value = "/rooms",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all rooms.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<Room> getAllRoom(@ApiParam(value = "The page number (zero-based)", required = true)
                                      @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @ApiParam(value = "Tha page size", required = true)
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.roomService.getAllRooms(page, size);
    }
    

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all reservations.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<Reservation> getAllReservations(@ApiParam(value = "The page number (zero-based)", required = true)
                                      @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @ApiParam(value = "Tha page size", required = true)
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.reservationService.getAllReservations(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single room.", notes = "You have to provide a valid room ID.")
    public
    @ResponseBody
    Room getRoom(@ApiParam(value = "The ID of the room.", required = true)
                             @PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Room room = this.roomService.getRoom(id);
        checkResourceFound(room);
        //todo: http://goo.gl/6iNAkz
        return room;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a room resource.", notes = "You have to provide a valid room ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateRoom(@ApiParam(value = "The ID of the existing room resource.", required = true)
                                 @PathVariable("id") Long id, @RequestBody Room room,
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.roomService.getRoom(id));
        if (id != room.getId()) throw new DataFormatException("ID doesn't match!");
        this.roomService.updateRoom(room);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a room resource.", notes = "You have to provide a valid room ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteRoom(@ApiParam(value = "The ID of the existing room resource.", required = true)
                                 @PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.roomService.getRoom(id));
        this.roomService.deleteRoom(id);
    }
}
