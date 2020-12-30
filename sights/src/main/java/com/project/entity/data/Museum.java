package com.project.entity.data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Museum extends Place{
    @Column(nullable = false)
    private String name;
}
