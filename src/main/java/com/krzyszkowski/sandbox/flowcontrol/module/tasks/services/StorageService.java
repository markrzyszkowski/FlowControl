package com.krzyszkowski.sandbox.flowcontrol.module.tasks.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void store(MultipartFile file, String filename);

    void delete(String filename);

    Resource loadAsResource(String filename);
}
