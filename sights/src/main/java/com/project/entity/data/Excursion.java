package com.project.entity.data;

import com.project.entity.data.address.City;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Excursion extends BaseData{

    private String startAddress;

    @Column
    private Type type;

    @ManyToMany(fetch = FetchType.EAGER)
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

    public Set<DayOfWeek> getWorkDays(List<Integer> set){
        Set<DayOfWeek> workDays = new HashSet<>();
        for (Integer i : set) {
            workDays.add(DayOfWeek.of(i));
        }
        return workDays;
    }

    public Set<NeedDate> getDates() {
        return dates;
    }

    public void setDates(Set<NeedDate> dates) {
        this.dates = dates;
    }
}
