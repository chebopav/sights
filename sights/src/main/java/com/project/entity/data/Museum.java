package com.project.entity.data;

import com.project.entity.data.address.City;
import com.project.helpers_and_statics.StaticVerifiers;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Museum extends BaseData {

    @Column(nullable = false)
    private String fullAddress;

    private String phone;

    private String email;

    private ArrayList<Integer> workDays = new ArrayList<>(7);

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
        if (!StaticVerifiers.verifiedEMail(email))
            throw new IllegalArgumentException("Некорректный e-mail");
        this.email = email;
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
