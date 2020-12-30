package com.project.entity.data;

import com.project.entity.data.address.City;

import javax.persistence.*;
import java.util.Objects;

/**
 * Базовый класс для всех мест
 * (в БД таблица не добавляется, но у всех наследников будут такие поля)
 */
@MappedSuperclass
public abstract class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @Column(nullable = false)
    private City city;

    @OneToOne
    private LifeHack lifeHack;

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
}
