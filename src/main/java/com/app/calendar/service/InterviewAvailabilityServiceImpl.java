package com.app.calendar.service;

import com.app.calendar.model.Candidate;
import com.app.calendar.model.GetAvailabiltyRequest;
import com.app.calendar.model.InterviewAvailability;
import com.app.calendar.model.Interviewer;
import com.app.calendar.model.utils.AvailabilitySlot;
import com.app.calendar.model.utils.TimeSlot;
import com.app.calendar.repository.InterviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InterviewAvailabilityServiceImpl implements InterviewAvailabilityService {

    @Autowired
    @Qualifier("candidateService")
    private CalendarService<Candidate> candidateService;

    @Autowired
    private InterviewerRepository interviewerRepository;


    @Override
    public InterviewAvailability getAvailability(GetAvailabiltyRequest availabiltyRequest) {
        if (availabiltyRequest.getCandidateId() == null
                || availabiltyRequest.getInterviewerIds() == null
                || availabiltyRequest.getInterviewerIds().size() == 0) {
            throw new IllegalArgumentException("Incorrect Data: Some input missing");
        }
        Candidate candidate = candidateService.getById(availabiltyRequest.getCandidateId());

        List<Interviewer> interviewers = interviewerRepository.findAllById(availabiltyRequest.getInterviewerIds());
        return getAvailabilityResult(candidate, interviewers);
    }

    private InterviewAvailability getAvailabilityResult(Candidate candidate, List<Interviewer> interviewers) {

        List<String> interviewerNames = interviewers.stream().map(x -> x.getName()).collect(Collectors.toList());

        return new InterviewAvailability(candidate.getName(), interviewerNames, calculateMatchingAvailability(candidate, interviewers));
    }

    private Set<AvailabilitySlot> calculateMatchingAvailability(Candidate candidate, List<Interviewer> interviewers) {
        return interviewers.stream().map(x -> calculateMatchingAvailability(candidate, x))
                .flatMap(l -> l.stream()).collect(Collectors.toSet());
    }

    private Set<AvailabilitySlot> calculateMatchingAvailability(Candidate candidate, Interviewer interviewer) {
        Set<AvailabilitySlot> availability = new HashSet<>();
        List<AvailabilitySlot> interviewerAvailability = interviewer.getAvailabilitySlotList();
        for (AvailabilitySlot candidateAvailabilityItem : candidate.getAvailabilitySlotList()) {
            for (AvailabilitySlot interviewAvailabilityItem : interviewerAvailability) {
                if (interviewAvailabilityItem.getDay().equals(candidateAvailabilityItem.getDay())) {
                    List<TimeSlot> commonTimeSlot = matchTimeSlot(candidateAvailabilityItem.getTimeSlotList(), interviewAvailabilityItem.getTimeSlotList());
                    if (commonTimeSlot.size() > 0) {
                        availability.add(new AvailabilitySlot(candidateAvailabilityItem.getDay(), commonTimeSlot));
                    }
                }
            }
        }

        return availability;

    }

    private List<TimeSlot> matchTimeSlot(List<TimeSlot> cTimeSlot, List<TimeSlot> iTimeSlot) {
        List<TimeSlot> commonTimeSlot = new ArrayList<>();
        cTimeSlot.stream().forEach(x -> {
            commonTimeSlot.addAll(iTimeSlot.stream().filter(y -> y.getFrom().compareTo(x.getFrom()) >= 0 && y.getTo().compareTo(x.getTo()) <= 0)
                    .collect(Collectors.toList()));
        });

        return commonTimeSlot;
    }
}
