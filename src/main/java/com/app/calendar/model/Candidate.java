package com.app.calendar.model;

import com.app.calendar.model.utils.AvailabilitySlot;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @NotNull
    @ElementCollection
    @Column(length = Integer.MAX_VALUE)
    private List<AvailabilitySlot> availabilitySlotList;


    public Candidate(){

    }

    public Candidate(String name) {
        this.name = name;
    }
    public Candidate(String name, List<AvailabilitySlot> availabilitySlotList) {
        this.name = name;
        this.availabilitySlotList = availabilitySlotList;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AvailabilitySlot> getAvailabilitySlotList() {
        return availabilitySlotList;
    }

    public void setAvailabilitySlotList(List<AvailabilitySlot> availabilitySlotList) {
        this.availabilitySlotList = availabilitySlotList;
    }
}
