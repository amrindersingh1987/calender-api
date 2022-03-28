package com.app.calendar.repository;

import com.app.calendar.model.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer, Long> {
    @Query("select i.name from Interviewer i")
    List<String> getAllNames();

    Optional<Interviewer> findByName(String name);

}
