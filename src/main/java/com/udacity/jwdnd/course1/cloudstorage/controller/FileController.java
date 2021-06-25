package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/files")
public class FileController {
    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam MultipartFile fileUpload, Principal principal) throws IOException {
        Integer userId = userService.getUserId(principal);
        fileService.addFile(fileUpload, userId);
        return "redirect:/home";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") Integer fileId, Principal principal) {
        Integer userId = userService.getUserId(principal);
        File file = fileService.getFile(fileId, userId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(httpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());

        return ResponseEntity.ok().headers(httpHeaders).body(resource);
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable(value = "fileId") Integer fileId, Principal principal) {
        Integer userId = userService.getUserId(principal);
        fileService.delete(fileId, userId);
        return "redirect:/home";
    }
}
