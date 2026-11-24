package ru.ksenia.NauJava.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ksenia.NauJava.dto.ImageUploadResponse;
import ru.ksenia.NauJava.exception.FileStorageException;
import ru.ksenia.NauJava.service.interfaces.CloudinaryService;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public ImageUploadResponse uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Файл не выбран");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Допускается загрузка только изображений");
        }
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                         "folder", "guitarManager",
                            "resource_type", "image",
                            "quality", "auto",
                            "fetch_format", "auto"

                    )
            );
            String imageUrl = uploadResult.get("secure_url").toString();
            return new ImageUploadResponse(imageUrl);
        }
        catch (IOException e) {
            throw new FileStorageException("Ошибка при загрузке изображения", e);
        }
    }

    @Override
    public void deleteImageByUrl(String imageUrl) {
        if (imageUrl == null || !imageUrl.contains("res.cloudinary.com")) return;
        String publicId = imageUrl.replaceAll("^.*/upload/(?:v\\d+/)?|\\.[a-zA-Z0-9]+$", "");
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }
        catch (IOException e) {
            throw new FileStorageException("Не удалось удалить изображение из облачного хранилища", e);
        }
    }
}
