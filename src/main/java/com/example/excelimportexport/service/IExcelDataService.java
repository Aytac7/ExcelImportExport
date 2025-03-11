package com.example.excelimportexport.service;

import java.util.List;

import com.example.excelimportexport.entity.User;

public interface IExcelDataService {

	List<User> getExcelDataAsList();
	
	int saveExcelData(List<User> users);
}