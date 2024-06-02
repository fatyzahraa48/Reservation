/**
 * 
 */
package com.zenika.example.service;
import java.util.ArrayList;
import java.util.List;

import com.zenika.example.domain.TimeSlotEnum;
/**
 * @author Z.Audit
 *
 */
public class TestSlots {
	
	

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

	    public static void main(String[] args) {
	        String startTime = "08";
	        String endTime = "12";

	        List<String> timeSlots = splitTimeRange(startTime, endTime);
	        for (String slot : timeSlots) {
	            System.out.println(slot);
	        }
	        List<String> reservations = new ArrayList<>();
	        reservations.add("08-09");
	        
	        timeSlots.removeAll(reservations);
	        System.out.println("available");
	        System.out.println(timeSlots);
	        
	        System.out.println(TimeSlotEnum.fromValue(timeSlots.get(0)));
	        
	        
	    }
	

	  /*  public static void main(String[] args) {
	        String startTime = "08:00 AM";
	        String endTime = "11:00 AM";
	        List<String[]> reservations = new ArrayList<>();
	        reservations.add(new String[]{"08:00 AM", "09:00 AM"});

	        List<String[]> timeSlots = generateTimeSlots(startTime, endTime, reservations);
	        System.out.println(timeSlots);
	        for (String[] slot : timeSlots) {
	            System.out.println(slot[0] + " - " + slot[1]);
	        }
	    }*/
	


}
