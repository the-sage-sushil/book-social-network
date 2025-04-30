package com.sushil.book.file;

import static java.io.File.separator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileManagementService {

    @Value("${application.file.uploads.photos-output-path}")
    private String fileUplaodPath;

    public String saveFile(
        @Nonnull Integer userId, 
        @Nonnull MultipartFile sourceFile) {
        // /uplaods/user/1/timemillsecond.ext
        final String fileUplaodSubPath = "user" + separator + userId;

        return uplaodFile(sourceFile, fileUplaodSubPath);
    }

    private String uplaodFile(
       @Nonnull MultipartFile sourceFile, 
       @Nonnull String fileUplaodSubPath) {

        final String finalUplaodPath = fileUplaodPath + separator + fileUplaodSubPath;

        File targetFolder = new File(finalUplaodPath);
        if(!targetFolder.exists()){
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.warn("Failed to create the target folder: " + targetFolder);
                return null;
            }
        }

        
        final String extension = getExtension(sourceFile);
        String targetFilePath = finalUplaodPath + separator + System.currentTimeMillis() + extension;

        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath,sourceFile.getBytes());
            log.info("File saved to: " + targetFilePath);
            return targetFilePath;
        }
        catch (IOException e) {
            log.error("File was not saved", e);
        }

        return null;
        
    }

    private String getExtension(MultipartFile sourceFile) {
        String fullFileName = sourceFile.getOriginalFilename();
        if(fullFileName.isEmpty() || fullFileName == null) return "";

        int lastDotIndex = fullFileName.lastIndexOf(".");
        if (lastDotIndex == -1) return "";

        return fullFileName.substring(lastDotIndex + 1);
    }


}
