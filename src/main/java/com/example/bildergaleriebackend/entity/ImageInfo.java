package com.example.bildergaleriebackend.entity;

import javax.persistence.*;

@Entity(name = "Image")
public class ImageInfo {

    @GeneratedValue
    @Id
    private int id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @Column(name = "timestamp")
    private long timestamp;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @Column(name = "creationTime")
    private String creationTime;

    @Column(name = "size")
    private String size;

    @Column(name = "isDirectory")
    private String isDirectory;

    @Column(name = "isSymbolicLink")
    private String isSymbolicLink;

    @Column(name = "isRegularFile")
    private String isRegularFile;

    @Column(name = "lastAccessTime")
    private String lastAccessTime;

    @Column(name = "fileKey")
    private String fileKey;



    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
