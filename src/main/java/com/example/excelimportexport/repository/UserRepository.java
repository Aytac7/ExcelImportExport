package com.example.excelimportexport.repository;
import com.example.excelimportexport.dto.UserDTO;
import com.example.excelimportexport.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    default public List<UserDTO> getUserList() {
//        List<UserDTO> list = new ArrayList<>();
//        list.add(UserDTO.builder().id(1L).name("aytac").surname("ramazanli").dob(LocalDateTime.parse("15.06.2004")).birthPlace("djdj").build());
//
//        list.add(UserDTO.builder().id(2L).name("user").surname("hesenli").dob(LocalDateTime.parse("15.07.2004")).birthPlace("aaa").build());
//
//        list.add(UserDTO.builder().id(3L).name("deni").surname("").dob(LocalDateTime.parse("15.06.2004")).birthPlace("").build());
//
//        return list;
//    }



}