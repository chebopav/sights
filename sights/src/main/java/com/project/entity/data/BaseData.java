package com.project.entity.data;

import com.project.entity.data.address.City;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Базовый класс для всех мест
 * (в БД таблица не добавляется, но у всех наследников будут такие поля)
 */
@MappedSuperclass
public abstract class BaseData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private City city;

    @Column(nullable = false)
    private String description;

    public BaseData() {
    }

    public BaseData(City city, String description) {
        this.setCity(city);
        this.setDescription(description);
    }

    public long getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        Objects.requireNonNull(city);
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().length() < 10)
            throw new IllegalArgumentException("Некорректное описание места");
        this.description = description;
    }
}
