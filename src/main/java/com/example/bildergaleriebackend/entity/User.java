package com.example.bildergaleriebackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity(name = "User")
public class User {

    @Id
    @NotBlank(message = "email_cannot_be_blank")
    @NotNull(message = "email_cannot_be_null")
    @Email(message = "email_format_invalid")
    private String email;

    @NotBlank(message = "name_cannot_be_blank")
    @NotNull(message = "name_cannot_be_null")
    @Column(name = "userName")
    private String userName;

    @NotBlank(message = "password_cannot_be_blank")
    @NotNull(message = "password_cannot_be_null")
    @Pattern(regexp = "(?=.*[A-Z]+)(?=.*[0-9]+).{8,}", message = "invalid_password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<ImageInfo> images;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ImageInfo> getImages() {
        return images;
    }

    public void setImages(List<ImageInfo> images) {
        this.images = images;
    }
}
