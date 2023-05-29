package com.telusko.springmvcboot.security.controller;

import com.telusko.springmvcboot.security.dto.LoginRequest;
import com.telusko.springmvcboot.security.dto.RegisterRequest;
import com.telusko.springmvcboot.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup (@RequestBody RegisterRequest registerRequest) {
        authService.signUp(registerRequest);

        //below code sends status of http request back to the client, you can check several status available
        //for learning
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        //on successful authentication a JWT token will be returned for a perticular user for which login is attempted
       return authService.login(loginRequest);
    }


}
