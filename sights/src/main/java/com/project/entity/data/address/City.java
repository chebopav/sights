package com.project.entity.data.address;

import com.project.entity.data.BaseData;

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
    @Column(nullable = false, unique = true)
    private Country country;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<BaseData> baseData = new HashSet<>();


    public City() {
    }

    public City(String name) {
        this.name = name;
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

    public Set<BaseData> getPlaces() {
        return baseData;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
