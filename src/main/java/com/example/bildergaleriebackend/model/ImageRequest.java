package com.example.bildergaleriebackend.model;

import javax.validation.constraints.NotNull;

public class ImageRequest {

    @NotNull
    private String email;

    @NotNull
    private int id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
