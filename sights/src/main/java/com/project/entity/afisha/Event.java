package com.project.entity.afisha;

import com.project.entity.data.Theater;
import com.project.entity.data.address.City;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Event {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    private String name;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Theater theater;

    public Event() {
    }

    public Event(String name, Theater theater, LocalDate date){
        this.setName(name);
        this.setDate(date);
        this.setTheater(theater);
    }

    public String getName() {
        return name;
    }

    public void setName(String name)  {
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

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public long getId() {
        return id;
    }

}
