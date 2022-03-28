package com.app.calendar.model;

import com.app.calendar.model.utils.AvailabilitySlot;

import java.util.List;
import java.util.Set;

public class InterviewAvailability {

    private final String candidateName;
    private final List<String> interviewersNames;
    private final Set<AvailabilitySlot> interviewAvailabilitySlotList;


    public InterviewAvailability(String candidateName, List<String> interviewersNames, Set<AvailabilitySlot> interviewAvailabilitySlotList) {
        this.candidateName = candidateName;
        this.interviewersNames = interviewersNames;
        this.interviewAvailabilitySlotList = interviewAvailabilitySlotList;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public List<String> getInterviewersNames() {
        return interviewersNames;
    }

    public Set<AvailabilitySlot> getInterviewAvailabilitySlotList() {
        return interviewAvailabilitySlotList;
    }
}
