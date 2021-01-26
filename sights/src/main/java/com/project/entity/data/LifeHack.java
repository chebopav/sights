package com.project.entity.data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Класс Лайфхаков
 * имеет связь много к 1 с местом
 */
@Entity
public class LifeHack {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(nullable = false)
    @Type(type = "text")
    private String text;

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
