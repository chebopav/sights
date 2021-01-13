package com.project.entity.data;

import com.project.entity.data.address.City;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.util.*;

@Entity
public class Excursion extends BaseData{

    private String name;

    @Column(nullable = false)
    private String startAddress;

    @Column(nullable = false)
    private Type type;

    private ArrayList<Integer> workDays = new ArrayList<>(7);

    public Excursion() {
    }

    public Excursion(City city, String description, String startAddress, Type type) {
        super(city, description);
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

    public List<Integer> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(ArrayList<Integer> workDays) {
        this.workDays = workDays;
    }

    public Set<DayOfWeek> getWorkDays(List<Integer> set){
        Set<DayOfWeek> workDays = new HashSet<>();
        for (Integer i : set) {
            workDays.add(DayOfWeek.of(i));
        }
        return workDays;
    }
}
