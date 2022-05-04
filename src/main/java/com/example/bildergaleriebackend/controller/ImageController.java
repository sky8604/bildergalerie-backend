package com.example.bildergaleriebackend.controller;

import com.example.bildergaleriebackend.model.ImageRequest;
import com.example.bildergaleriebackend.service.ImageService;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Stateless
@Path("image")
public class ImageController {

    @Inject
    private ImageService imageService;

    @POST
    @Path("uploadImageData")
    public Response upload(@Valid ImageRequest imageRequest) {
        System.out.println(imageRequest.toString());
        return Response
                .status(200)
                .entity(imageRequest)
                .build();
    }

    @POST
    @Path("uploadImage")
    public Response image(@Valid File image) {
        try {
            imageService.saveImage(image);
        } catch (IOException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e)
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(image)
                .build();
    }

    @GET
    @Path("downloadImageById")
    @Produces("image/png")
    public Response getImageById(@QueryParam("id") int id) {
        try {
            File image = imageService.getImageById(id);
            Response.ResponseBuilder responseBuilder = Response.ok(image);
            responseBuilder.header("Content-Disposition", "attachment; filename=" + image.getName());
            return responseBuilder.build();
        }
        catch (IOException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e)
                    .build();
        }


    }
}
