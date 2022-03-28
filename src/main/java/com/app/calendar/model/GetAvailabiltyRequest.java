package com.app.calendar.model;


import javax.validation.constraints.NotNull;
import java.util.List;

public class GetAvailabiltyRequest {
    @NotNull
    private Long candidateId;
    @NotNull
    private List<Long> interviewerIds;

    public Long getCandidateId() {
        return candidateId;
    }

    public List<Long> getInterviewerIds() {
        return interviewerIds;
    }
}
