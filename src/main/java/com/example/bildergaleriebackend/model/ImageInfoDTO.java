package com.example.bildergaleriebackend.model;

import javax.persistence.Column;

public class ImageInfoDTO {

    private String title;
    private String description;
    private String userName;
    private int id;
    private String creationTime;
    private String size;
    private String isDirectory;
    private String isSymbolicLink;
    private String isRegularFile;
    private String lastAccessTime;
    private String fileKey;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(String isDirectory) {
        this.isDirectory = isDirectory;
    }

    public String getIsSymbolicLink() {
        return isSymbolicLink;
    }

    public void setIsSymbolicLink(String isSymbolicLink) {
        this.isSymbolicLink = isSymbolicLink;
    }

    public String getIsRegularFile() {
        return isRegularFile;
    }

    public void setIsRegularFile(String isRegularFile) {
        this.isRegularFile = isRegularFile;
    }

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(String lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}
