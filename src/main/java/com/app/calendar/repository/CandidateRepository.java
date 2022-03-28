package com.app.calendar.repository;

import com.app.calendar.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query("select c.name from Candidate c")
    List<String> getAllNames();

    @Query("select candidate from Candidate candidate where candidate.name = :name")
    Optional<Candidate> findByName(String name);

}
