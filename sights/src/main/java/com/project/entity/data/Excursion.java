package com.project.entity.data;

import com.project.entity.data.address.City;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.DayOfWeek;
import java.util.*;

@Entity
public class Excursion extends BaseData{

    @Column(nullable = false)
    private String startAddress;

    @Column(nullable = false)
    private Type type;

    //
    @Transient
    private Set<DayOfWeek> workDays = new HashSet<>(7);

    //
    @Transient
    private Map<DayOfWeek, Map<String, Integer>> priceList = new HashMap<>();

    public Excursion() {
    }

    public Excursion(City city, String description, String startAddress, Type type, int[] workDays) {
        super(city, description);
        this.setStartAddress(startAddress);
        this.setType(type);
        for (int workDay : workDays) {
            this.workDays.add(DayOfWeek.of(workDay));
        }
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

    public Set<DayOfWeek> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Set<DayOfWeek> workDays) {
        Objects.requireNonNull(workDays);
        this.workDays = workDays;
    }

    public Map<DayOfWeek, Map<String, Integer>> getPriceList() {
        return priceList;
    }

    public void setPriceList(Map<DayOfWeek, Map<String, Integer>> priceList) {
        Objects.requireNonNull(priceList);
        this.priceList = priceList;
    }

    public enum Type{
        BUS,
        WATER,
        ON_FOOT,
        COMPLEX
    }
}
