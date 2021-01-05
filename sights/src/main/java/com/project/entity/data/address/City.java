package com.project.entity.data.address;

import com.project.entity.data.Excursion;
import com.project.entity.data.Museum;
import com.project.entity.data.Sight;
import com.project.exceptions.ArgumentException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Museum> museums = new HashSet<>();

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Sight> sights = new HashSet<>();

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Excursion> excursions = new HashSet<>();

    public City() {
    }

    public City(String name) throws ArgumentException{
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

    public void setName(String name) throws ArgumentException {
        if (name == null || name.trim().length() < 4)
            throw new ArgumentException("Некорректное название города");
        this.name = name;
    }

    public Set<Museum> getMuseums() {
        return museums;
    }

    public void setMuseums(Set<Museum> museums) throws ArgumentException {
        if(museums == null)
            throw new ArgumentException("Музеи не могут быть null");
        this.museums = museums;
    }

    public Set<Sight> getSights() {
        return sights;
    }

    public void setSights(Set<Sight> sights) throws ArgumentException {
        if(sights == null)
            throw new ArgumentException("Достопримечательности не могут быть null");
        this.sights = sights;
    }

    public Set<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(Set<Excursion> excursions) throws ArgumentException {
        if(excursions == null)
            throw new ArgumentException("Экскурсии не могут быть null");
        this.excursions = excursions;
    }
}
