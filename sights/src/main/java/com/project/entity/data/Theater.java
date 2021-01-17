package com.project.entity.data;

import com.project.entity.afisha.Event;
import com.project.entity.data.address.City;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Theater extends BaseData{

    private String fullAddress;

    private String phone;

    @OneToMany(mappedBy = "theater", fetch = FetchType.EAGER)
    private Set<Event> events = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    public Theater() {
    }

    public Theater(String name){
        this.setName(name);
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
