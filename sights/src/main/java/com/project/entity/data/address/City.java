package com.project.entity.data.address;

import com.project.entity.afisha.Event;
import com.project.entity.data.Excursion;
import com.project.entity.data.Museum;
import com.project.entity.data.Sight;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class City {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Event> events = new HashSet<>();

    public City() {
    }

    public City(String name){
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

    public void setMuseums(Set<Museum> museums){
        if(museums == null)
            throw new IllegalArgumentException("Музеи не могут быть null");
        this.museums = museums;
    }

    public Set<Sight> getSights() {
        return sights;
    }

    public void setSights(Set<Sight> sights){
        if(sights == null)
            throw new IllegalArgumentException("Достопримечательности не могут быть null");
        this.sights = sights;
    }

    public Set<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(Set<Excursion> excursions){
        if(excursions == null)
            throw new IllegalArgumentException("Экскурсии не могут быть null");
        this.excursions = excursions;
    }
}
