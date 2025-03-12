package com.example.excelimportexport.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

//	@Value("${app.upload.file}")
//	public String EXCEL_FILE_PATH;


	private final UserRepository userRepository;

	Workbook workbook;

	public List<User> getExcelDataAsList() {

		List<String> list = new ArrayList<String>();


		DataFormatter dataFormatter = new DataFormatter();

		try {
			workbook = WorkbookFactory.create(new File("C:\\Users\\avramazanli\\IdeaProjects\\ExcelImportExport\\files\\UserExcel_20.xlsx"));
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

		List<User> invList = createList(list, noOfColumns, sheet);

		try {
			workbook.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return invList;
	}

	private List<User> createList(List<String> excelData, int noOfColumns, Sheet sheet) {
		ArrayList<User> invList = new ArrayList<User>();
		int i = 10;
		do {
			User user = new User();
			user.setId(Long.parseLong(excelData.get(i)));
			user.setName(excelData.get(i+1));
			user.setSurname(excelData.get(i+2));
			if(excelData.get(i+3) != null && !excelData.get(i + 3).isEmpty()) {
				String dateString = excelData.get(i + 3);
				if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
					System.out.println(dateString);
					try {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate dateTime = LocalDate.parse(dateString, formatter);
						user.setDob(dateTime);
						System.out.println("Parse olunan zaman: " + dateTime);
					} catch (DateTimeParseException e) {
						System.out.println("Stringi parse etmək mümkün olmadı.");
					}
				}
			}
			user.setBirthPlace(excelData.get(i+4));
			//System.out.println(excelData.get(i+3));
			//user.setDob(LocalDateTime.parse(excelData.get(i+3)));
//			System.out.println(excelData.get(i + 3));
//			user.setDob(LocalDateTime.parse(excelData.get(i + 3)));

			invList.add(user);
			i+=5;

		} while (i < excelData.size());
		System.out.println(invList);
		return invList;
	}



	@Override
	public int saveExcelData(List<User> users) {
		users = userRepository.saveAll(users);
		return users.size();
	}
}