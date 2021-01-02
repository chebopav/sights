package com.project.entity.data;

import com.project.entity.data.address.City;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Sight extends BaseData {

    @Column(nullable = false)
    private String fullAddress;

    public Sight() {
    }

    public Sight(City city, String fullAddress, String description) {
        super(city, description);
        this.setFullAddress(fullAddress);
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        if (fullAddress == null || fullAddress.trim().length() < 5)
            throw new IllegalArgumentException("Некорректный адрес старта экскурсии");
        this.fullAddress = fullAddress;
    }
}
