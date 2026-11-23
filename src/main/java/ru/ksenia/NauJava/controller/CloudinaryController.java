package ru.ksenia.NauJava.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.ksenia.NauJava.dto.ImageUploadResponse;
import ru.ksenia.NauJava.service.interfaces.CloudinaryService;

@RestController
@RequestMapping("/api/files")
public class CloudinaryController {
    private final CloudinaryService cloudinaryService;

    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ImageUploadResponse uploadPhoto(@RequestParam("file") MultipartFile file) {
        return cloudinaryService.uploadImage(file);
    }
}
