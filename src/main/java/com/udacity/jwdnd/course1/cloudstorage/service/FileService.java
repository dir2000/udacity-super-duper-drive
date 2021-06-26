package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void addFile(MultipartFile fileUpload, Integer userId) throws IOException {
        File file = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                String.valueOf(fileUpload.getSize()), userId, fileUpload.getBytes());
        fileMapper.insert(file);
    }

    public List<File> getFiles(Integer userId) {
        return fileMapper.getFiles(userId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public void delete(Integer fileId) {
        fileMapper.delete(fileId);
    }

    public boolean fileExists(String fileName, Integer userId) {
        return fileMapper.getFileByName(fileName, userId) != null;
    }
}
