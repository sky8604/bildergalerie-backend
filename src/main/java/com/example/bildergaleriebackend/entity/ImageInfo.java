package com.example.bildergaleriebackend.entity;

import javax.persistence.*;

@Entity(name = "image")
public class ImageInfo {

    @Id
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
