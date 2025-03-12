package com.example.excelimportexport.service;


import com.example.excelimportexport.dto.UserDTO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserExportToExcelService extends ReportAbstract {

    public void writeTableData(Object data) {

        List<UserDTO> list = (List<UserDTO>) data;


        CellStyle style = getFontContentExcel();


        int startRow = 2;

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (UserDTO userDTO : list) {
            Row row = sheet.createRow(startRow++);
            int columnCount = 0;

            createCell(row, columnCount++, userDTO.getId(), style);
            createCell(row, columnCount++, userDTO.getName(), style);
            createCell(row, columnCount++, userDTO.getSurname(), style);String dob= (userDTO.getDob()!=null )? userDTO.getDob().format(formatter) : "N/A";
            createCell(row, columnCount++, dob, style);
            createCell(row, columnCount++, userDTO.getBirthPlace(), style);





        }
    }


    public void exportToExcel(HttpServletResponse response, Object data) throws IOException {
        newReportExcel();


        response = initResponseForExportExcel(response, "UserExcel");
        ServletOutputStream outputStream = response.getOutputStream();


        String[] headers = new String[]{"No", "name", "surname", "dob", "birthPlace"};
        writeTableHeaderExcel("Sheet User", "Report User", headers);

        writeTableData(data);

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}