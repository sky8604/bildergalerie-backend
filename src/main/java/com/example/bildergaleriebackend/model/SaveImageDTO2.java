package com.example.bildergaleriebackend.model;

import java.io.File;

public class SaveImageDTO2 {

    private File file;
    private String email;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
