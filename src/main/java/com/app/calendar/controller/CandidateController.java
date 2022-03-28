package com.app.calendar.controller;

import com.app.calendar.model.Candidate;
import com.app.calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/candidates")
public class CandidateController {

    @Autowired
    private CalendarService<Candidate> calendarService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Candidate create(@Valid @RequestBody Candidate candidate) {
        return calendarService.create(candidate);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Candidate> getAll() {
        return calendarService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Candidate getByName(@PathVariable Long id) {
        return calendarService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        calendarService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Candidate update(@PathVariable Long id, @Valid @RequestBody Candidate candidate) {
       return calendarService.update(id, candidate);
    }
}
