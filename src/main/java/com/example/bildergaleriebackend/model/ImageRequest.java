package com.example.bildergaleriebackend.model;

import javax.validation.constraints.NotNull;
import java.io.File;

public class ImageRequest {

    @NotNull
    private String email;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private File image;

}
