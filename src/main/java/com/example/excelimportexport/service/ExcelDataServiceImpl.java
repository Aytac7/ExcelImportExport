package com.example.excelimportexport.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.excelimportexport.entity.User;
import com.example.excelimportexport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ExcelDataServiceImpl implements IExcelDataService {

	@Value("${app.upload.file:${user.home}}")
	public String EXCEL_FILE_PATH;


	private final UserRepository userRepository;

	Workbook workbook;

	public List<User> getExcelDataAsList() {

		List<String> list = new ArrayList<String>();

		DataFormatter dataFormatter = new DataFormatter();

		try {
			workbook = WorkbookFactory.create(new File(EXCEL_FILE_PATH));
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}

		System.out.println("-------Workbook has '" + workbook.getNumberOfSheets() + "' Sheets-----");

		Sheet sheet = workbook.getSheetAt(0);

		int noOfColumns = sheet.getRow(0).getLastCellNum();
		System.out.println("-------Sheet has '"+noOfColumns+"' columns------");

		for (Row row : sheet) {
			for (Cell cell : row) {
				String cellValue = dataFormatter.formatCellValue(cell);
				list.add(cellValue);
			}
		}

		List<User> invList = createList(list, noOfColumns);

		try {
			workbook.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return invList;
	}

	private List<User> createList(List<String> excelData, int noOfColumns) {

		ArrayList<User> invList = new ArrayList<User>();

		int i = noOfColumns;
		do {
			User user = new User();

			user.setName(excelData.get(i));
			user.setSurname(excelData.get(i+1));
			user.setBirthPlace(excelData.get(i+2));
			user.setDob(LocalDateTime.parse(excelData.get(i+3)));
//			user.setReceivedDate(excelData.get(i + 4));

			invList.add(user);
			i = i + (noOfColumns);

		} while (i < excelData.size());
		return invList;
	}



	@Override
	public int saveExcelData(List<User> users) {
		users = userRepository.saveAll(users);
		return users.size();
	}
}