package com.app.calendar.service;

import com.app.calendar.exception.ConstraintsViolationException;
import com.app.calendar.model.Interviewer;
import com.app.calendar.model.utils.AvailabilitySlot;
import com.app.calendar.model.utils.TimeSlot;

import com.app.calendar.repository.InterviewerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InterviewServiceImplTests {
    @Mock
    private InterviewerRepository interviewerRepository;

    @InjectMocks
    private InterviewerServiceImpl interviewerServiceImpl;
    private String name="David";

    private List<AvailabilitySlot> slots = new ArrayList<>(){{
        add(new AvailabilitySlot(
                        LocalDate.parse("2022-03-28"), new ArrayList<TimeSlot>() {{
                    add(new TimeSlot(LocalTime.parse("13:00"), LocalTime.parse("14:00")));
                    add(new TimeSlot(LocalTime.parse("15:00"), LocalTime.parse("16:00")));
                }})
        );}};


    @Test
    public void testCreateInterviewWithAvailbility() {

        Interviewer interviewer = new Interviewer(name, slots);
        when(interviewerRepository.save(interviewer)).thenReturn(interviewer);

        Interviewer result = interviewerServiceImpl.create(interviewer);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(1,result.getAvailabilitySlotList().size());


    }
    @Test
    public void testDuplicateInterviewRecordExpection(){
        Interviewer interviewer = new Interviewer(name, slots);
        when(interviewerRepository.findByName(name)).thenReturn(Optional.ofNullable(interviewer));
        assertThrows(ConstraintsViolationException.class,() ->{ interviewerServiceImpl.create(interviewer); });
    }

    @Test
    public void testCreateInterviewWithOutNameExpection() {
        Interviewer interviewer = new Interviewer("");
        assertThrows(ConstraintsViolationException.class,() ->{ interviewerServiceImpl.create(interviewer); });
    }

    @Test
    public void testGetInterviewById(){
        Long id= 1L;
        Interviewer interviewer = new Interviewer(name, slots);
        interviewer.setId(id);
        when(interviewerRepository.findById(id)).thenReturn(Optional.ofNullable(interviewer));
        Interviewer result = interviewerServiceImpl.getById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void testGetInterviewByIdEntityNotFoundException(){
        Long id= 1L;
        when(interviewerRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        assertThrows(EntityNotFoundException.class,() ->{ interviewerServiceImpl.getById(id); });
    }

    @Test
    public void deleteInterviewById() {
        Long id = 1L;
        Interviewer interviewer = new Interviewer(name, slots);
        interviewer.setId(id);
        when(interviewerRepository.findById(id)).thenReturn(Optional.ofNullable(interviewer));
        interviewerServiceImpl.delete(1l);
        verify(interviewerRepository, times(1)).deleteById(1l);
    }

    @Test
    public void deleteInterviewByIdEXCEPTION() {
        Long id = 1L;
        when(interviewerRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        assertThrows(EntityNotFoundException.class,() ->{ interviewerServiceImpl.delete(id); });
    }
}
