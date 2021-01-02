package com.project.entity.data;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс Лайфхаков
 * имеет связь много к 1 с местом
 */
@Entity
public class LifeHack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    private BaseData baseData;

    public LifeHack() {
    }

    public LifeHack(String text) {
        this.setText(text);
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null || text.trim().length() < 5)
            throw new IllegalArgumentException("Некорректное описание лайфхака");
        this.text = text;
    }
}
