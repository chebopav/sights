package com.project.entity.data;

import com.project.entity.data.address.City;
import com.project.helpers_and_statics.WorkDays;

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

    private WorkDays[] workDays;

    public Excursion() {
    }

    public Excursion(City city, String description, String startAddress, Type type, WorkDays...days) {
        super(city, description);
        this.setStartAddress(startAddress);
        this.setType(type);
        workDays = new WorkDays[days.length];
        for (int i = 0; i < days.length; i++) {
            workDays[i] = days[i];
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

    public enum Type{
        BUS,
        WATER,
        ON_FOOT,
        COMPLEX
    }
}
