package com.example.bildergaleriebackend.service;

import com.example.bildergaleriebackend.entity.ImageInfo;
import com.example.bildergaleriebackend.model.Base64DTO;
import com.example.bildergaleriebackend.model.ImageInfoDTO;
import com.example.bildergaleriebackend.model.ImageRequest;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Stateless
public class ImageService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserService userService;

    @Transactional
    public int saveImage(File image) throws IOException {
        long currentTime = System.currentTimeMillis();
        BasicFileAttributes attributes = getMetaData(image);
        String creationTime = attributes.creationTime().toString();
        String size = String.valueOf(attributes.size());
        String isDirectory = String.valueOf(attributes.isDirectory());
        String isSymbolicLink = String.valueOf(attributes.isSymbolicLink());
        String isRegularFile = String.valueOf(attributes.isRegularFile());
        String lastAccessTime = attributes.lastAccessTime().toString();
        String fileKey = attributes.fileKey().toString();
        em.createNativeQuery("INSERT INTO  Image (image, timestamp, creationTime, size, isDirectory, isSymbolicLink, isRegularFile, lastAccessTime, fileKey) VALUES (?,?,?,?,?,?,?,?,?)")
                .setParameter(1, convertToByteStream(image))
                .setParameter(2, currentTime)
                .setParameter(3, creationTime)
                .setParameter(4, size)
                .setParameter(5, isDirectory)
                .setParameter(6, isSymbolicLink)
                .setParameter(7, isRegularFile)
                .setParameter(8, lastAccessTime)
                .setParameter(9, fileKey)
                .executeUpdate();
        return getIdFromTimestamp(currentTime);
    }

    public File getImageById(int id) throws IOException {
        List<ImageInfo> images = getImageInfoById(id);
        if (images.size() == 1) {
            return convertToImage(images.get(0).getImage(), id);
        }
        return null;
    }

    public List<Base64DTO> getAllBase64() throws IOException {
        List<ImageInfo> images = getAllimageInfo();
        List<Base64DTO> base64DTOS = new ArrayList<>();
        for (ImageInfo image : images) {
            File file = convertToImage(image.getImage(), image.getId());
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            inputStream.read(bytes);
            Base64DTO base64DTO = new Base64DTO();
            base64DTO.setBase64(new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8));
            base64DTO.setId(image.getId());
            base64DTOS.add(base64DTO);
        }
        return base64DTOS;
    }

    public Base64DTO getBase64Id(int id) throws IOException {
        ImageInfo imageInfo = getImageInfoById(id).get(0);
        File file = convertToImage(imageInfo.getImage(), id);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        inputStream.read(bytes);
        Base64DTO base64DTO = new Base64DTO();
        base64DTO.setBase64(new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8));
        base64DTO.setId(imageInfo.getId());
        return base64DTO;
    }

    public ImageInfo getImageDataById(int id) {
        List<ImageInfo> images = getImageInfoById(id);
        if (images.size() == 1) {
            return images.get(0);
        }
        return null;
    }

    public void saveImageDataById(ImageRequest imageRequest) {
            ImageInfo imageInfo = getImageInfoById(imageRequest.getId()).get(0);
            em.remove(imageInfo);
            imageInfo.setDescription(imageRequest.getDescription());
            imageInfo.setTitle(imageRequest.getTitle());
            imageInfo.setUser(userService.getUserByEmail(imageRequest.getEmail()));
            em.persist(imageInfo);
    }

    public ImageInfo deleteImageById(int id) {
        ImageInfo imageInfo = getImageInfoById(id).get(0);
        em.remove(imageInfo);
        return imageInfo;
    }

    public ImageInfo updateDescription(String newDescription, int id) {
        ImageInfo imageInfo = getImageInfoById(id).get(0);
        em.remove(imageInfo);
        imageInfo.setDescription(newDescription);
        em.persist(imageInfo);
        return imageInfo;
    }

    public int getLastId() {
        List<ImageInfo> imageInfos = em.createQuery("SELECT i FROM Image i", ImageInfo.class).getResultList();
        List<Integer> integers = new ArrayList<>();
        for (ImageInfo imageInfo : imageInfos) {
            integers.add(imageInfo.getId());
        }
        return Collections.max(integers);
    }

    private byte[] convertToByteStream(File image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        return outputStream.toByteArray();
    }

    private File convertToImage(byte[] bytes, int id) throws IOException {
        File responseFile = File.createTempFile("prefix" + id, ".png");
        try (FileOutputStream outputStream = new FileOutputStream(responseFile)) {
            outputStream.write(bytes);
        }
        return new File(responseFile.getAbsolutePath());
    }

    private List<ImageInfo> getImageInfoById(int id) {
        Query query = em.createQuery("SELECT i FROM Image i WHERE i.id = :id");
        query.setParameter("id", id);
        return (List<ImageInfo>) query.getResultList();
    }

    private List<ImageInfo> getAllimageInfo() {
        Query query = em.createQuery("SELECT i FROM Image i");
        return (List<ImageInfo>) query.getResultList();
    }

    private int getIdFromTimestamp (long timestamp) {
        Query query = em.createQuery("SELECT i FROM Image i WHERE i.timestamp = :timestamp");
        query.setParameter("timestamp", timestamp);
        List<Object> list = query.getResultList();
        if (list.size() == 1) {
            if (list.get(0) instanceof ImageInfo) {
                return ((ImageInfo) list.get(0)).getId();
            }
        }
        return 0;
    }

    private BasicFileAttributes getMetaData(File file) throws IOException {
        File metaDataFile = File.createTempFile("metadata", ".png");
        FileReader reader = new FileReader(file);
        FileWriter writer = new FileWriter(metaDataFile);
        String str = "";
        int i;
        while ((i = reader.read()) != -1) {
            str += (char)i;
        }
        writer.write(str);
        reader.close();
        writer.close();
        return Files.readAttributes(metaDataFile.toPath(), BasicFileAttributes.class);
    }
}
