package com.example.excelimportexport.service.export;


import com.example.excelimportexport.dto.UserDTO;
import com.example.excelimportexport.entity.User;
import com.example.excelimportexport.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.IOException;
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



}