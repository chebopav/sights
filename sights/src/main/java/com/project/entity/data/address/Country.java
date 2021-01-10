package com.project.entity.data.address;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Country {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<City> cities = new HashSet<>();

    public Country() {
    }

    public Country(String name){
        this.setName(name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        if (name == null || name.trim().length() < 3)
            throw new IllegalArgumentException("Некорректное название страны");
        this.name = name;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public void addCity(City city){
        cities.add(city);
        city.setCountry(this);
    }


}
