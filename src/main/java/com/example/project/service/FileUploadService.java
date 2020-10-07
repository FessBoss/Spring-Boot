package com.example.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileUploadService {
    public void upload(MultipartFile file) {
        File convertFile = new File("D:\\project\\src\\images\\upload\\" + file.getOriginalFilename());
        try {
            convertFile.createNewFile();
            try (FileOutputStream fileOutputStream = new FileOutputStream(convertFile)) {
                fileOutputStream.write(file.getBytes());
            } catch (IOException exe) {
                exe.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
