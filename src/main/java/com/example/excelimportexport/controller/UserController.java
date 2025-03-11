//package com.example.excelimportexport.controller;
//
//import java.util.List;
//
//import com.example.excelimportexport.entity.User;
//import com.example.excelimportexport.repository.UserRepository;
//import com.example.excelimportexport.service.IExcelDataService;
//import com.example.excelimportexport.service.IFileUploaderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//
//@Controller
//@RequiredArgsConstructor
//public class UserController {
//
//
//    private final IFileUploaderService fileService;
//
//    private final IExcelDataService excelService;
//
//    private final UserRepository repository;
//
//
////    @GetMapping("/")
////    public String index() {
////        return "pages/uploadPage";
////    }
//
//    @GetMapping("/uploadFile")
//    public String index() {
//        return "uploadPage";
//    }
//    @PostMapping("/uploadFile")
//    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//
//        fileService.uploadFile(file);
//
//        redirectAttributes.addFlashAttribute("message",
//            "You have successfully uploaded '"+ file.getOriginalFilename()+"' !");
//        try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//
//			e.printStackTrace();
//		}
//        return "redirect:/";
//    }
//
//    @GetMapping("/saveData")
//    public String saveExcelData(Model model) {
//
//    	List<User> excelDataAsList = excelService.getExcelDataAsList();
//    	int noOfRecords = excelService.saveExcelData(excelDataAsList);
//    	model.addAttribute("noOfRecords",noOfRecords);
//    	return "pages/success";
//    }
//}