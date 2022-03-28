package com.app.calendar.service;

import com.app.calendar.exception.ConstraintsViolationException;
import com.app.calendar.model.Candidate;
import com.app.calendar.model.utils.AvailabilitySlot;
import com.app.calendar.model.utils.TimeSlot;
import com.app.calendar.repository.CandidateRepository;
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
public class CandidateServiceImplTests {
    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateServiceImpl candidateServiceImpl;
    private String name="Carl";

    private List<AvailabilitySlot> slots = new ArrayList<>(){{
        add(new AvailabilitySlot(
                        LocalDate.parse("2022-03-28"), new ArrayList<TimeSlot>() {{
                    add(new TimeSlot(LocalTime.parse("13:00"), LocalTime.parse("14:00")));
                    add(new TimeSlot(LocalTime.parse("15:00"), LocalTime.parse("16:00")));
                }})
        );}};


    @Test
    public void testCreateCandidateWithAvailbility() {

        Candidate candidate = new Candidate(name, slots);
        when(candidateRepository.save(candidate)).thenReturn(candidate);

        Candidate savedCandidate = candidateServiceImpl.create(candidate);

        assertNotNull(savedCandidate);
        assertEquals(name, savedCandidate.getName());
        assertEquals(1,savedCandidate.getAvailabilitySlotList().size());


    }
    @Test
    public void testDuplicateCandidateRecordExpection(){
        Candidate candidate = new Candidate(name, slots);
        when(candidateRepository.findByName(name)).thenReturn(Optional.ofNullable(candidate));
        assertThrows(ConstraintsViolationException.class,() ->{ candidateServiceImpl.create(candidate); });
    }

    @Test
    public void testCreateCandidateWithOutNameExpection() {
        Candidate candidate = new Candidate("");
        assertThrows(ConstraintsViolationException.class,() ->{ candidateServiceImpl.create(candidate); });
    }

    @Test
    public void testGetCandidateById(){
        Long id= 1L;
        Candidate candidate = new Candidate(name, slots);
        candidate.setId(id);
        when(candidateRepository.findById(id)).thenReturn(Optional.ofNullable(candidate));
        Candidate result = candidateServiceImpl.getById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void testGetCandidateByIdEntityNotFoundException(){
        Long id= 1L;
        when(candidateRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        assertThrows(EntityNotFoundException.class,() ->{ candidateServiceImpl.getById(id); });
    }

    @Test
    public void deleteCandidateById() {
        Long id = 1L;
        Candidate candidate = new Candidate(name, slots);
        candidate.setId(id);
        when(candidateRepository.findById(id)).thenReturn(Optional.ofNullable(candidate));
        candidateServiceImpl.delete(1l);
        verify(candidateRepository, times(1)).deleteById(1l);
    }

    @Test
    public void deleteCandidateByIdEXCEPTION() {
        Long id = 1L;
        when(candidateRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        assertThrows(EntityNotFoundException.class,() ->{ candidateServiceImpl.delete(id); });
    }
}
