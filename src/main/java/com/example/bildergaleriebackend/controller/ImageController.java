package com.example.bildergaleriebackend.controller;

import com.example.bildergaleriebackend.entity.ImageInfo;
import com.example.bildergaleriebackend.model.DescriptionRequest;
import com.example.bildergaleriebackend.model.ImageInfoDTO;
import com.example.bildergaleriebackend.model.ImageRequest;
import com.example.bildergaleriebackend.model.SaveImageDTO;
import com.example.bildergaleriebackend.service.ImageService;
import com.example.bildergaleriebackend.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

@Stateless
@Path("image")
public class ImageController {

    @Inject
    private ImageService imageService;

    @Inject
    private UserService userService;

    @POST
    @Path("uploadImageData")
    public Response upload(@Valid ImageRequest imageRequest) {
        imageService.saveImageDataById(imageRequest);
        return Response
                .status(200)
                .entity(imageRequest)
                .build();
    }

    @POST
    @Path("uploadImage")
    public Response image(@Valid File image) {
        try {
            SaveImageDTO dto = new SaveImageDTO();
            dto.setId(imageService.saveImage(image));
            return Response
                    .status(Response.Status.OK)
                    .entity(dto)
                    .build();
        } catch (IOException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e)
                    .build();
        }

    }

    @GET
    @Path("downloadImageById")
    @Produces("image/png")
    public Response getImageById(@QueryParam("id") int id) {
        try {
            File image = imageService.getImageById(id);
            Response.ResponseBuilder responseBuilder = Response.ok();
            responseBuilder.header("Content-Disposition", "attachment; filename=" + image.getName());
            return responseBuilder.build();

        } catch (IOException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e)
                    .build();
        }
    }

    @GET
    @Path("downloadBase64")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBase64() {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(imageService.getAllBase64())
                    .build();
        } catch (IOException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e)
                    .build();
        }
    }

    @GET
    @Path("downloadBase64Id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBase64(@QueryParam("id") String id) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(imageService.getBase64Id(Integer.parseInt(id)))
                    .build();
        } catch (IOException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e)
                    .build();
        }
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@QueryParam("id") String id) {
        ImageInfoDTO dto = new ImageInfoDTO();
        ImageInfo imageInfo = imageService.deleteImageById(Integer.parseInt(id));
        dto.setDescription(imageInfo.getDescription());
        dto.setTitle(imageInfo.getTitle());
        dto.setId(imageInfo.getId());
        dto.setUserName(imageInfo.getUser().getUserName());
        return Response
                .status(Response.Status.OK)
                .entity(dto)
                .build();
    }

    @PATCH
    @Path("updateDescription")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDescription(DescriptionRequest descriptionRequest) {
        ImageInfoDTO dto = new ImageInfoDTO();
        ImageInfo imageInfo = imageService.updateDescription(descriptionRequest.getDescription(), Integer.parseInt(descriptionRequest.getId()));
        dto.setDescription(imageInfo.getDescription());
        dto.setTitle(imageInfo.getTitle());
        dto.setId(imageInfo.getId());
        dto.setUserName(userService.getUserByEmail(descriptionRequest.getEmail()).getUserName());
        return Response
                .status(Response.Status.OK)
                .entity(dto)
                .build();
    }

    @GET
    @Path("imageData")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImageDataById(@QueryParam("id") String id, @QueryParam("email") String email) {
        ImageInfoDTO dto = new ImageInfoDTO();
        ImageInfo imageInfo = imageService.getImageDataById(Integer.parseInt(id));
        dto.setTitle(imageInfo.getTitle());
        dto.setDescription(imageInfo.getDescription());
        dto.setUserName(userService.getUserByEmail(email).getUserName());
        dto.setId(Integer.parseInt(id));
        dto.setCreationTime(imageInfo.getCreationTime());
        dto.setFileKey(imageInfo.getFileKey());
        dto.setIsDirectory(imageInfo.getIsDirectory());
        dto.setIsRegularFile(imageInfo.getIsRegularFile());
        dto.setSize(imageInfo.getSize());
        dto.setIsSymbolicLink(imageInfo.getIsSymbolicLink());
        dto.setLastAccessTime(imageInfo.getLastAccessTime());
        return Response
                .status(Response.Status.OK)
                .entity(dto)
                .build();
    }
}
