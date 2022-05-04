package com.example.bildergaleriebackend.service;

import com.example.bildergaleriebackend.entity.ImageInfo;
import com.example.bildergaleriebackend.model.ImageInfoDTO;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@Stateless
public class ImageService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveImage(File image) throws IOException {
        em.createNativeQuery("INSERT INTO  Image (image, timestamp) VALUES (?,?)")
                .setParameter(1, convertToByteStream(image))
                .setParameter(2, System.currentTimeMillis())
                .executeUpdate();
    }

    public File getImageById(int id) throws IOException {
        List<ImageInfo> images = getImageInfoById(id);
        if (images.size() == 1) {
            return convertToImage(images.get(0).getImage(), id);
        }
        return null;
    }

    public ImageInfoDTO getImageDataById(int id) {
        List<ImageInfo> images = getImageInfoById(id);
        if (images.size() == 1) {
            ImageInfo imageInfo = images.get(0);
            ImageInfoDTO dto = new ImageInfoDTO();
            dto.setTitle(imageInfo.getTitle());
            dto.setDescription(imageInfo.getDescription());
            dto.setUserName(imageInfo.getUser().getUserName());
            return dto;
        }
        return null;
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
        String tmpdir = System.getProperty("java.io.tmpdir");
        return new File(responseFile.getAbsolutePath());
    }

    private List<ImageInfo> getImageInfoById(int id) {
        Query query = em.createQuery("SELECT i FROM Image i WHERE i.id = :id");
        query.setParameter("id", id);
        return (List<ImageInfo>) query.getResultList();
    }
}
