package ru.ksenia.NauJava.service.interfaces;

import com.cloudinary.Cloudinary;
import org.springframework.web.multipart.MultipartFile;
import ru.ksenia.NauJava.dto.ImageUploadResponse;

public interface CloudinaryService {
    public ImageUploadResponse uploadImage(MultipartFile file);
    void deleteImageByUrl(String imageUrl);
}
