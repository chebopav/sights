package com.project.entity.data;

import com.project.entity.data.address.City;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Museum extends BaseData {

    @Column(nullable = false)
    private String fullAddress;

    private String phone;

    private String email;

    public Museum() {
    }

    public Museum(City city, String name, String fullAddress, String description, String phone) {
        super(city, name, description);
        this.setFullAddress(fullAddress);
        this.setPhone(phone);
    }


    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        if (fullAddress == null || fullAddress.trim().length() < 5)
           throw new IllegalArgumentException("Некорректный адрес музея");
        this.fullAddress = fullAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone.contains("[a-zA-Z]"))
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
