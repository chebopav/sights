package com.project.entity.data;

import com.project.entity.data.address.City;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Базовый класс для всех мест
 * (в БД таблица не добавляется, но у всех наследников будут такие поля)
 */
@MappedSuperclass
public abstract class BaseData {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @ManyToOne
    private City city;

    @Column(unique = true, nullable = false)
    private String name;

    @Type(type = "text")
    private String description;

    private double rating;

    private long rateCount;

    @ManyToMany
    private Set<Comment> comments;

    public BaseData() {
    }

    public BaseData(City city, String name, String description) {
        this.setCity(city);
        this.setDescription(description);
        this.setName(name);
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getRateCount() {
        return rateCount;
    }

    public void setRateCount(long rateCount) {
        this.rateCount = rateCount;
    }

    public void addRating(int newRating){
        if (newRating < 0 || newRating > 5){
            throw new IllegalArgumentException("Некорректная оценка");
        }
        this.rating = ((this.rating * rateCount) + newRating) / (rateCount + 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 3)
            throw new IllegalArgumentException("Некорректное название места");
        this.name = name;
    }
}
