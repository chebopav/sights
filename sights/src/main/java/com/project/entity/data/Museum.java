package com.project.entity.data;

import com.project.entity.data.address.City;
import com.project.helpers_and_statics.StaticVerifiers;
import com.project.helpers_and_statics.WorkDays;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Museum extends BaseData {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String fullAddress;

    private String phone;

    private String email;

    private WorkDays[] workDays;

    public Museum() {
    }

    public Museum(City city, String name, String fullAddress, String description, String phone, WorkDays...days) {
        super(city, description);
        this.setName(name);
        this.setFullAddress(fullAddress);
        this.setPhone(phone);
        workDays = new WorkDays[days.length];
        for (int i = 0; i < days.length; i++) {
            workDays[i] = days[i];
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

    public WorkDays[] getWorkDays() {
        return workDays;
    }

    public void setWorkDays(WorkDays[] workDays) {
        this.workDays = workDays;
    }
}
