package com.project.entity.data;

import com.project.entity.data.address.City;
import com.project.helpers_and_statics.StaticVerifiers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class Museum extends BaseData {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String fullAddress;

    //
    @Transient
    private Set<DayOfWeek> workDays = new HashSet<>(7);

    @Transient
    private Map<DayOfWeek, Map<String, Integer>> priceList = new HashMap<>();


    private String phone;

    private String email;

    public Museum() {
    }

    public Museum(City city, String name, String fullAddress, String description, String phone, int[] workDays) {
        super(city, description);
        this.setName(name);
        this.setFullAddress(fullAddress);
        this.setPhone(phone);
        for (int workDay : workDays) {
            this.workDays.add(DayOfWeek.of(workDay));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 3)
            throw new IllegalArgumentException("Некорректное название музея");
        this.name = name;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        if (fullAddress == null || fullAddress.trim().length() < 5)
           throw new IllegalArgumentException("Некорректный адрес музея");
        this.fullAddress = fullAddress;
    }

    public Set<DayOfWeek> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Set<DayOfWeek> workDays) {
        this.workDays = workDays;
    }

    public Map<DayOfWeek, Map<String, Integer>> getPriceList() {
        return priceList;
    }

    public void setPriceList(Map<DayOfWeek, Map<String, Integer>> priceList) {
        this.priceList = priceList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        // TODO: Проверка номера телефона из строки
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
}
