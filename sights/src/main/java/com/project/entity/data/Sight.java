package com.project.entity.data;

import com.project.entity.data.address.City;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Sight extends BaseData {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String fullAddress;

    public Sight() {
    }

    public Sight(String name, City city, String fullAddress, String description) {
        super(city, description);
        this.setFullAddress(fullAddress);
        this.setName(name);
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        if (fullAddress == null || fullAddress.trim().length() < 5)
            throw new IllegalArgumentException("Некорректный адрес старта экскурсии");
        this.fullAddress = fullAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 4)
            throw new IllegalArgumentException("Некорректное имя достопримчательности");
        this.name = name;
    }
}
