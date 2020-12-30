package com.project.entity.data;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс Лайфхаков
 * имеет связь 1 к 1 с местом
 */
@Entity
public class LifeHack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String text;

    @OneToOne(mappedBy = "lifeHack")
    private Place place;

    public LifeHack() {
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        Objects.requireNonNull(text);
        this.text = text;
    }
}
