package com.app.calendar.service;

import com.app.calendar.exception.ConstraintsViolationException;
import com.app.calendar.model.Interviewer;
import com.app.calendar.model.utils.AvailabilitySlot;
import com.app.calendar.model.utils.TimeSlot;
import com.app.calendar.repository.InterviewerRepository;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("interviewerService")
public class InterviewerServiceImpl implements CalendarService<Interviewer> {
    private final InterviewerRepository interviewerRepository;

    @Autowired
    public InterviewerServiceImpl(InterviewerRepository interviewerRepository) {
        this.interviewerRepository = interviewerRepository;
    }

    /**
     * Create new  interviewer
     * @param interviewer
     * @return Interviewer
     */
    @Override
    public Interviewer create(Interviewer interviewer) {
        if (Strings.isNullOrEmpty(interviewer.getName())
                || interviewer.getAvailabilitySlotList().size() == 0) {
            throw new ConstraintsViolationException("Invalid Data: Required Information missing");
        }
        Optional<Interviewer> alreadyRecord = interviewerRepository.findByName(interviewer.getName());
        if (alreadyRecord.isPresent()) {
            throw new ConstraintsViolationException("Name already exists!" + interviewer.getName());
        }
        return interviewerRepository.save(interviewer);
    }

    /**
     * Get all Interviewers
     *
     * @return List
     */
    @Override
    public List<Interviewer> getAll() {
        return interviewerRepository.findAll();
    }

    /**
     * Get Interviewer om the bases of name
     *
     * @param name
     * @return Interviewer
     */
    @Override
    public Interviewer getByName(String name) {
        return interviewerRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with name: " + name));
    }

    /**
     * Get Interviewer on the bases of id
     *
     * @param id
     * @return Interviewer
     */
    @Override
    public Interviewer getById(Long id) {
        return interviewerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
    }

    /**
     * delete the Interviewer on the bases of id
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        getById(id);
        interviewerRepository.deleteById(id);
    }

    /**
     * Update the Interviewer on the bases of id
     *
     * @param id
     * @param interviewer
     * @return
     */
    @Override
    public Interviewer update(Long id, Interviewer interviewer) {
        Interviewer interviewer1 = getById(id);
        Map<LocalDate, List<TimeSlot>> dayMaps = interviewer.getAvailabilitySlotList().stream()
                .collect(Collectors.toMap(AvailabilitySlot::getDay, AvailabilitySlot::getTimeSlotList));

        interviewer1.getAvailabilitySlotList().stream().forEach(x -> {
            if(dayMaps.get(x.getDay())!=null) {
                x.getTimeSlotList().addAll(dayMaps.get(x.getDay()));
                dayMaps.remove(x.getDay());
            }
        });

        dayMaps.entrySet().stream().forEach(x-> {
            interviewer1.getAvailabilitySlotList().add(new AvailabilitySlot(x.getKey(),x.getValue()));
        });
        return interviewerRepository.save(interviewer1);
    }

}
