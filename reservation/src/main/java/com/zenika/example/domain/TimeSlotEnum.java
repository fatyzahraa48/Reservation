package com.zenika.example.domain;

public enum TimeSlotEnum {
	    HOUR_08_09("08-09"),
	    HOUR_09_10("09-10"),
	    HOUR_10_11("10-11"),
	    HOUR_11_12("11-12"),
	    HOUR_12_13("12-13"),
	    HOUR_13_14("13-14"),
	    HOUR_14_15("14-15"),
	    HOUR_15_16("15-16"),
	    HOUR_16_17("16-17"),
	    HOUR_17_18("17-18"),
	    HOUR_18_19("18-19"),
	    HOUR_19_20("19-20");

	    private final String range;

	    TimeSlotEnum(String range) {
	        this.range = range;
	    }

	    public String getRange() {
	        return range;
	    }
	    public static TimeSlotEnum fromValue(String value) {
	        for (TimeSlotEnum slot : TimeSlotEnum.values()) {
	            if (slot.getRange().equals(value)) {
	                return slot;
	            }
	        }
	        throw new IllegalArgumentException("Unknown value: " + value);
	    }
}
