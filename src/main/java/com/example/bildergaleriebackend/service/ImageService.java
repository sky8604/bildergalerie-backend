package com.example.bildergaleriebackend.service;

import com.example.bildergaleriebackend.common.FileFormat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageService {

    private byte[] convertToByteStream(File image, FileFormat format) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (format == FileFormat.JPG){
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } else if (format == FileFormat.PNG) {
            ImageIO.write(bufferedImage, "png", outputStream);
        }
        return outputStream.toByteArray();
    }

    private BufferedImage convertToImage(byte[] bytes) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(inputStream);
    }
}
