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




}