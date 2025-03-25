package com.modakdev.models.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "file_system")
public class ModakFlixFileSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "LONGTEXT")
    private String name;

    @Column(name = "category", nullable = false, length = 20)
    private String category;

    @Column(name = "path", nullable = false, columnDefinition = "LONGTEXT")
    private String path;

    @Column(name = "language", nullable = false, length = 20)
    private String language;

    @Column(name = "des", nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "album_art_path", nullable = false, columnDefinition = "LONGTEXT")
    private String albumArtPath;

    @Column(name = "url", nullable = false, columnDefinition = "LONGTEXT")
    private String url;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlbumArtPath() {
        return albumArtPath;
    }

    public void setAlbumArtPath(String albumArtPath) {
        this.albumArtPath = albumArtPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

