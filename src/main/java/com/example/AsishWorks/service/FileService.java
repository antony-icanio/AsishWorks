package com.example.AsishWorks.service;

import com.example.AsishWorks.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.zip.ZipOutputStream;

public class FileService {
    public String addPhoto(String title, MultipartFile image) {
        return null;
    }

    public Photo getPhoto(String id) {
        return null;
    }

    public void zipFile(File fileToZip, String name, ZipOutputStream zipOut) {
    }
}
