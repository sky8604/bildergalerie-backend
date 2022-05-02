package com.example.bildergaleriebackend.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AuthRequestDTO {

    @Email(message = "email_format_invalid")
    @NotBlank(message = "email_cannot_be_blank")
    @NotNull(message = "email_cannot_be_null")
    private String email;

    @NotNull(message = "password_cannot_be_null")
    @NotBlank(message = "password_cannot_be_blank")
    @Pattern(regexp = "(?=.*[A-Z]+)(?=.*[0-9]+).{8,}")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
