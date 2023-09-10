package com.telusko.springmvcboot.security.controller;

import com.telusko.springmvcboot.Service.GymService;
import com.telusko.springmvcboot.security.dto.LoginRequest;
import com.telusko.springmvcboot.security.dto.RegisterRequest;
import com.telusko.springmvcboot.security.exception.ErrorMessages;
import com.telusko.springmvcboot.security.service.AuthService;
import com.telusko.springmvcboot.security.service.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup (@RequestBody RegisterRequest registerRequest) {
        boolean result = authService.signUp(registerRequest);

        //below code sends status of http request back to the client, you can check several status available
        //for learning
        return result ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(ErrorMessages.UserExist,HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
        //on successful authentication a JWT token will be returned for a perticular user for which login is attempted
       return authService.login(loginRequest);
    }
}
