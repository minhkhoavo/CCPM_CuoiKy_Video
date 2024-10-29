package com.restaurant.management.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public Map upload(MultipartFile file)  {
        try{
            return this.cloudinary.uploader().upload(file.getBytes(), Map.of());
        } catch (IOException io){
            throw new RuntimeException("Image upload fail");
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
        return uploadResult.get("url").toString();
    }

}