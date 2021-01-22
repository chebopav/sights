package com.project.entity.data;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.entity.afisha.Event;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class NeedDate {
    @Id
    private LocalDate date;

    @ManyToMany(mappedBy = "dates", fetch = FetchType.EAGER)
    private Set<Excursion> excursions;

    @ManyToMany(mappedBy = "dates", fetch = FetchType.EAGER)
    private Set<Event> events;

    public NeedDate() {
    }

    public NeedDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(Set<Excursion> excursions) {
        this.excursions = excursions;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event){
        events.add(event);
    }

    public void addExcursion(Excursion excursion){
        excursions.add(excursion);
    }

}
