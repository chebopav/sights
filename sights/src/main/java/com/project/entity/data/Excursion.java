package com.project.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.entity.data.address.City;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Excursion extends BaseData{

    private String startAddress;

    @Column
    private Type type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<NeedDate> dates = new HashSet<>();

    public Excursion() {
    }

    public Excursion(City city, String name, String description, String startAddress, Type type) {
        super(city, name, description);
        this.setStartAddress(startAddress);
        this.setType(type);
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        if (startAddress == null || startAddress.trim().length() < 5)
            throw new IllegalArgumentException("Некорректный адрес начала экскурсии");
        this.startAddress = startAddress;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        BUS,
        WATER,
        ON_FOOT,
        COMPLEX
    }

    public Set<NeedDate> getDates() {
        return dates;
    }

    public void setDates(Set<NeedDate> dates) {
        this.dates = dates;
    }
}