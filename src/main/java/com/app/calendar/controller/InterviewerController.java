package com.app.calendar.controller;

import com.app.calendar.model.Interviewer;
import com.app.calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/interviewers")
public class InterviewerController {
    @Autowired
    private CalendarService<Interviewer> calendarService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Interviewer create(@Valid @RequestBody Interviewer interviewer) {
        return calendarService.create(interviewer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Interviewer> getAll() {
        return calendarService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Interviewer getById(@PathVariable Long id) {
        return calendarService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        calendarService.delete(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Interviewer update(@PathVariable Long id, @Valid @RequestBody Interviewer interviewer) {
       return calendarService.update(id, interviewer);
    }

}
