package com.telusko.springmvcboot.security.dto;

import com.telusko.springmvcboot.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select id from User where username= :userName")
    Long getIdByUserName (@Param("userName") String userName);

}
