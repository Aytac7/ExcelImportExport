package com.example.excelimportexport.controller;

import com.example.excelimportexport.entity.User;
import com.example.excelimportexport.service.IExcelDataService;
import com.example.excelimportexport.service.IFileUploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserImportController {

    private final IFileUploaderService fileService;

    private final IExcelDataService excelService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileService.uploadFile(file);
            String message = "You have successfully uploaded '" + file.getOriginalFilename() + "'!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            String errorMessage = "Could not upload the file. Please try again!";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/saveData")
    public ResponseEntity<Object> saveExcelData() {
        try {
            List<User> excelDataAsList = excelService.getExcelDataAsList();
            System.out.println(excelDataAsList);
            int noOfRecords = excelService.saveExcelData(excelDataAsList);
            return ResponseEntity.status(HttpStatus.OK).body("Number of records saved: " + noOfRecords);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the data.");
        }
    }


}
