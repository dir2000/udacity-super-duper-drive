package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.MediaType;
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
    public String uploadFile(@RequestParam MultipartFile fileUpload, Principal principal, Model model) throws IOException {
        Integer userId = userService.getUserId(principal);
        if ("".equals(fileUpload.getOriginalFilename())) {
            model.addAttribute("errorMessage", "You should select a file.");
            return "result";
        } else if (fileService.fileExists(fileUpload.getOriginalFilename(), userId)) {
            model.addAttribute("errorMessage", "File with such name already exists.");
            return "result";
        }
        Integer rowCountAffected = fileService.addFile(fileUpload, userId);
        addChangesInfoToModel(rowCountAffected, model);
        return "result";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") Integer fileId) {
        File file = fileService.getFile(fileId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(httpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType())).headers(httpHeaders).body(resource);
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable(value = "fileId") Integer fileId, Model model) {
        Integer rowCountAffected = fileService.delete(fileId);
        addChangesInfoToModel(rowCountAffected, model);
        return "result";
    }

    private void addChangesInfoToModel(Integer rowCountAffected, Model model) {
        if (rowCountAffected == 0) {
            model.addAttribute("changesNotSaved", true);
        } else {
            model.addAttribute("changesSaved", true);
        };
    }
}
