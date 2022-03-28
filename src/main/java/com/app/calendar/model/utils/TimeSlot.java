package com.app.calendar.model.utils;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot implements Serializable {
    private LocalTime from;
    private LocalTime to;

    public TimeSlot() {
    }

    public TimeSlot(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalTime getFrom() {
        return from;
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public LocalTime getTo() {
        return to;
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(from, timeSlot.from) &&
                Objects.equals(to, timeSlot.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
