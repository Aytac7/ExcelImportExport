package com.example.excelimportexport.service.export;


import com.example.excelimportexport.dto.UserDTO;
import com.example.excelimportexport.entity.User;
import com.example.excelimportexport.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserReportService {

    private final UserExportToExcelService userExportToExcelService;


    private final UserRepository userRepository;




    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<User> users = userRepository.findAll();


        List<UserDTO> userDTOs = users.stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .dob(user.getDob() != null ? user.getDob() : null)
                        .birthPlace(user.getBirthPlace())
                        .build())
                .collect(Collectors.toList());


        userExportToExcelService.exportToExcel(response, userDTOs);
    }


    public void exportToExcelV2() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("User Report");
        List<User> data = userRepository.findAll();
        if (!data.isEmpty()) {
            Row headerRow = sheet.createRow(0);
            int columnIndex = 0;

            CellStyle headerCellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerCellStyle.setFont(font);

            List<String> headerList = new ArrayList<String>();
            headerList.add("No");
            headerList.add("Name");
            headerList.add("Surname");
            headerList.add("DOB");
            headerList.add("BirthPlace");
            for (String header : headerList) {
                Cell headerCell = headerRow.createCell(columnIndex++);
                headerCell.setCellValue(header);
                headerCell.setCellStyle(headerCellStyle);
            }


            int rowIndex = 1;
            for (User user : data) {
                Row dataRow = sheet.createRow(rowIndex++);
                columnIndex = 0;
                dataRow.createCell(0).setCellValue(user.getId());
                dataRow.createCell(1).setCellValue(user.getName());
                dataRow.createCell(2).setCellValue(user.getSurname());
                dataRow.createCell(3).setCellValue(user.getDob());
                dataRow.createCell(4).setCellValue(user.getBirthPlace());
            }
            for (int index = 0; index <= columnIndex; index++) {
                sheet.setColumnWidth(index, 5000);
            }


            try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\avramazanli\\IdeaProjects\\ExcelImportExport\\files\\UserReportTest.xlsx")) {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }

            workbook.close();

        }
    }


}