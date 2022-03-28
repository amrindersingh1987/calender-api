package com.app.calendar.service;

import com.app.calendar.model.GetAvailabiltyRequest;
import com.app.calendar.model.InterviewAvailability;

import java.util.Set;

public interface InterviewAvailabilityService {
    InterviewAvailability getAvailability(GetAvailabiltyRequest availabiltyReques);
}
