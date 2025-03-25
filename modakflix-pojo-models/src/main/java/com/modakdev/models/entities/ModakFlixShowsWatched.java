package com.modakdev.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "shows_watched")
public class ModakFlixShowsWatched {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serial", nullable = false, unique = true)
    private Long serial;

    @Column(name = "url", nullable = false, columnDefinition = "LONGTEXT")
    private String url;

    @Column(name = "position", nullable = false)
    private Long position;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "album_art_path", nullable = false, columnDefinition = "LONGTEXT")
    private String albumArtPath;

    @Column(name = "des", nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "duration", nullable = false)
    private Long duration;

    @Column(name = "cause", nullable = false, length = 30)
    private String cause;

    @Column(name = "name", nullable = false, columnDefinition = "LONGTEXT")
    private String name;

    @Column(name = "id", nullable = false, unique = true)
    private long showId;

    // Getters and Setters
    public Long getSerial() {
        return serial;
    }

    public void setSerial(Long serial) {
        this.serial = serial;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlbumArtPath() {
        return albumArtPath;
    }

    public void setAlbumArtPath(String albumArtPath) {
        this.albumArtPath = albumArtPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getShowId() {
        return showId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }
}
