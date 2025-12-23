package com.example.teckzite.entity;

import jakarta.persistence.*;
@Entity
public class Techie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long techieId;
    private String name;
    private String college;
    private double amountPaid;
    private int numberOfEvents;
    private String skills;
    private String interest;
    private String interests;
    // Getters and Setters

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public Long getTechieId() {
        return techieId;
    }

    public void setTechieId(Long techieId) {
        this.techieId = techieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getNumberOfEvents() {
        return numberOfEvents;
    }

    public void setNumberOfEvents(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
