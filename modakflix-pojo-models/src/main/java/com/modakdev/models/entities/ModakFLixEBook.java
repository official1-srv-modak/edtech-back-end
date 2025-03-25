package com.modakdev.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ebook")
public class ModakFLixEBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path", unique = true, nullable = false)
    private String path;

    @Column(nullable = false)
    private String name;

    // Default constructor
    public ModakFLixEBook() {
    }

    // Constructor with ID
    public ModakFLixEBook(Long id, String path, String name) {
        this.id = id;
        this.path = path;
        this.name = name;
    }

    // Constructor without ID
    public ModakFLixEBook(String path, String name) {
        this.path = path;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ModakFLixEBook{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
