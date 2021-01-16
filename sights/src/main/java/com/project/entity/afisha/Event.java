package com.project.entity.afisha;

import com.project.entity.data.NeedDate;
import com.project.entity.data.Theater;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Theater theater;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<NeedDate> dates = new HashSet<>();

    public Event() {
    }

    public Event(String name, Theater theater){
        this.setName(name);
        this.setTheater(theater);
    }

    public String getName() {
        return name;
    }

    public void setName(String name)  {
        this.name = name;
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

    public Set<NeedDate> getDates() {
        return dates;
    }

    public void setDates(Set<NeedDate> dates) {
        this.dates = dates;
    }
}
