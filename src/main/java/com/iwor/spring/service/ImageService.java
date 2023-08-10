package com.iwor.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${app.image.bucket:images}")
    private final String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        var fullImagePath = getFullPath(imagePath);
        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> download(String imagePath) {
        var fullPath = getFullPath(imagePath);
        return Files.exists(fullPath)
                ? Optional.of(Files.readAllBytes(fullPath))
                : Optional.empty();
    }

    private Path getFullPath(String imagePath) {
        return Path.of(bucket, imagePath);
    }
}
