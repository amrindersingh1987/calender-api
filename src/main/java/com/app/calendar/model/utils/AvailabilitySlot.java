package com.app.calendar.model.utils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class AvailabilitySlot implements Serializable {
    private final LocalDate day;
    private final List<TimeSlot> timeSlotList;

    public AvailabilitySlot(LocalDate day, List<TimeSlot> timeSlotList) {
        this.day = day;
        this.timeSlotList = timeSlotList;
    }

    public LocalDate getDay() {
        return day;
    }

    public List<TimeSlot> getTimeSlotList() {
        return timeSlotList;
    }
}
