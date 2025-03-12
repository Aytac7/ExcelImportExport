package com.example.excelimportexport.controller;

import com.example.excelimportexport.entity.User;
import com.example.excelimportexport.repository.UserRepository;
import com.example.excelimportexport.service.IExcelDataService;
import com.example.excelimportexport.service.IFileUploaderService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final IFileUploaderService fileService;

    private final IExcelDataService excelService;

//    @GetMapping("/uploadFileRest")
//    public String index() {
//        return "uploadPage";
//    }
//    @PostMapping("/uploadFile")
//    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//
//        fileService.uploadFile(file);
//
//        redirectAttributes.addFlashAttribute("message",
//                "You have successfully uploaded '"+ file.getOriginalFilename()+"' !");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//
//            e.printStackTrace();
//        }
//        return "redirect:/";
//    }
//
//    @GetMapping("/saveData")
//    public String saveExcelData(Model model) {
//
//        List<User> excelDataAsList = excelService.getExcelDataAsList();
//        int noOfRecords = excelService.saveExcelData(excelDataAsList);
//        model.addAttribute("noOfRecords",noOfRecords);
//        return "pages/success";
//    }

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

//    @GetMapping("/saveData")
//    public ResponseEntity<Object> saveExcelData(@RequestParam String filePath) {
//        try {
//            Path path = Paths.get(filePath);
//            if (!Files.exists(path)) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found at: " + filePath);
//            }
//
//            FileInputStream fileInputStream = new FileInputStream(path.toFile());
//            Workbook workbook = WorkbookFactory.create(fileInputStream);
//
//            List<User> excelDataAsList = excelService.getExcelDataAsList(workbook);
//            int noOfRecords = excelService.saveExcelData(excelDataAsList);
//
//            return ResponseEntity.status(HttpStatus.OK).body("Number of records saved: " + noOfRecords);
//        } catch (FileNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + e.getMessage());
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file: " + e.getMessage());
//        }
//    }

}
