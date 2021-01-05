package com.project.entity.data.address;

import com.project.entity.data.Excursion;
import com.project.entity.data.Museum;
import com.project.entity.data.Sight;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @Column(nullable = false, unique = true)
    private Country country;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Museum> museums = new HashSet<>();

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Sight> sights = new HashSet<>();

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Excursion> excursions = new HashSet<>();

    public City() {
    }

    public City(String name) {
        this.setName(name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 4)
            throw new IllegalArgumentException("Некорректное название города");
        this.name = name;
    }


    public Set<Museum> getMuseums() {
        return museums;
    }

    public void setMuseums(Set<Museum> museums) {
        Objects.requireNonNull(museums);
        this.museums = museums;
    }

    public Set<Sight> getSights() {
        return sights;
    }

    public void setSights(Set<Sight> sights) {
        Objects.requireNonNull(sights);
        this.sights = sights;
    }

    public Set<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(Set<Excursion> excursions) {
        Objects.requireNonNull(excursions);
        this.excursions = excursions;
    }
}
