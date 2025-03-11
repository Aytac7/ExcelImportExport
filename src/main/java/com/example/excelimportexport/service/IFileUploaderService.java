package com.example.excelimportexport.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploaderService {

	 void uploadFile(MultipartFile file);
}