package com.app.calendar.controller;

import com.app.calendar.model.GetAvailabiltyRequest;
import com.app.calendar.model.InterviewAvailability;
import com.app.calendar.service.InterviewAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/availability")
public class InterviewAvailabilityController {
    @Autowired
    private InterviewAvailabilityService interviewAvailabilityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public InterviewAvailability getAvailability(
            @Valid @RequestBody GetAvailabiltyRequest availabiltyRequest) {
        return interviewAvailabilityService.getAvailability(availabiltyRequest);
    }
}
