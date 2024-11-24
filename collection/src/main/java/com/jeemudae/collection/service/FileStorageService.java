package com.jeemudae.collection.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path rootLocation = Paths.get("uploads");

    public FileStorageService() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le dossier de stockage des fichiers.", e);
        }
    }

    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Échec de l'upload : fichier vide.");
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Tentative de stockage dans un emplacement non autorisé.");
            }

            file.transferTo(destinationFile);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Échec de l'upload du fichier.", e);
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
}
