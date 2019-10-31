package com.krzyszkowski.sandbox.flowcontrol.module.tasks.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {

    private static final Path storage = Paths.get(System.getProperty("java.io.tmpdir"), "FlowControl");

    @Override
    public void store(MultipartFile file, String filename) {
        var directory = storage.toFile();
        directory.mkdirs();
        var path = Paths.get(storage.toString(), filename);
        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String filename) {
        var path = Paths.get(storage.toString(), filename);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        var path = Paths.get(storage.toString(), filename);
        return new FileSystemResource(path);
    }
}
