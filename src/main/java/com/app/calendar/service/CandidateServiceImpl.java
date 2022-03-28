package com.app.calendar.service;

import com.app.calendar.exception.ConstraintsViolationException;
import com.app.calendar.model.Candidate;
import com.app.calendar.model.utils.AvailabilitySlot;
import com.app.calendar.model.utils.TimeSlot;
import com.app.calendar.repository.CandidateRepository;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("candidateService")
public class CandidateServiceImpl implements CalendarService<Candidate> {
    private final CandidateRepository candidateRepository;


    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    /**
     * Create new candidate
     * @param candidate
     * @return Candidate
     */
    @Override
    public Candidate create(Candidate candidate) {
        if (Strings.isNullOrEmpty(candidate.getName()) || candidate.getAvailabilitySlotList() == null
                || candidate.getAvailabilitySlotList().size() ==0) {
            throw new ConstraintsViolationException("Invalid Data: Required Information missing");
        }

        verifyUniqueRecord(candidate.getName());
        return   candidateRepository.save(candidate);
    }

    /**
     * Get All  Candidates
     * @return List
     */
    @Override
    public List<Candidate> getAll() {
        return candidateRepository.findAll();
    }

    /**
     * Get Candidate bases of name
     * @param name
     * @return Candidate
     */
    @Override
    public Candidate getByName(String name) {
        return candidateRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with name: " + name));
    }

    /**
     * get Candidate bases on the id
     * @param id
     * @return Candidate
     */
    @Override
    public Candidate getById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
    }

    /**
     * Delete the Candidate
     * @param id
     */
    @Override
    public void delete(Long id) {
        getById(id);
        candidateRepository.deleteById(id);
    }

    /**
     * Update the Candidate on the bases of id
     * @param id
     * @param candidate
     * @return
     */
    @Override
    public Candidate update(Long id, Candidate candidate) {
        Candidate candidate1 = getById(id);
        Map<LocalDate, List<TimeSlot>> dayMaps = candidate.getAvailabilitySlotList().stream()
                .collect(Collectors.toMap(AvailabilitySlot::getDay, AvailabilitySlot::getTimeSlotList));

        candidate1.getAvailabilitySlotList().stream().forEach(x -> {
            if(dayMaps.get(x.getDay())!=null) {
                x.getTimeSlotList().addAll(dayMaps.get(x.getDay()));
                dayMaps.remove(x.getDay());
            }
        });

        dayMaps.entrySet().stream().forEach(x-> {
            candidate1.getAvailabilitySlotList().add(new AvailabilitySlot(x.getKey(),x.getValue()));
        });
        return candidateRepository.save(candidate1);
    }



    private void verifyUniqueRecord(String name) {
        Optional<Candidate> existingNames = candidateRepository.findByName(name);
        if (existingNames.isPresent()) {
            throw new ConstraintsViolationException("User with name alreasy exist: " + name);
        }

    }
}
