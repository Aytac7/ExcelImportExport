package com.example.excelimportexport.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    Long id;
    String name;
    String surname;
    LocalDate dob;
    String birthPlace;
}
