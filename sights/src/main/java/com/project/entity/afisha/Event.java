package com.project.entity.afisha;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String theater;

    public Event() {
    }

    public Event(String name,  String theater, LocalDate date){
        this.setName(name);
        this.setDate(date);
        this.setTheater(theater);
    }

    public String getName() {
        return name;
    }

    public void setName(String name)  {
        if (name == null || name.trim().length() < 3)
            throw new IllegalArgumentException("Некорректное название события");
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Некорректная дата");
        this.date = date;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public long getId() {
        return id;
    }
}
