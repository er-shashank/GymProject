package com.telusko.springmvcboot.security.controller;

import com.telusko.springmvcboot.security.dto.LoginRequest;
import com.telusko.springmvcboot.security.dto.RegisterRequest;
import com.telusko.springmvcboot.utilities.OTPgenerator;
import com.telusko.springmvcboot.utilities.constants.ErrorMessages;
import com.telusko.springmvcboot.security.service.AuthService;
import com.telusko.springmvcboot.security.service.AuthenticationResponse;
import com.telusko.springmvcboot.security.service.EmailServiceImpl;
import com.telusko.springmvcboot.utilities.constants.SimpleMessageStrings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        //here we are validating otp, username and email
        ErrorMessages result = authService.signUp(registerRequest);

        return new ResponseEntity(result.getErrorMsg(), result.getHttpStatus());
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
        //on successful authentication a JWT token will be returned for a perticular user for which login is attempted
        return authService.login(loginRequest);
    }

    @GetMapping("/otp")
    public ResponseEntity generateOtp(@RequestBody RegisterRequest requestBody) throws Exception {

        /*
         * for sing-up: checks if username or email already exist
         * */
        ErrorMessages result = authService.validate(requestBody);

        if (result != ErrorMessages.NoError) {
            log.debug(getClass().getName() + "Entered data has duplicate properties");
            return new ResponseEntity(result, HttpStatus.CONFLICT);
        }


        //to check if emtered email is valid
        String regexPattern = "^(.+)@(\\S+)$";
        boolean isValidEmail = Pattern.compile(regexPattern)
                .matcher(requestBody.getEmail())
                .matches();

        if (!isValidEmail) {
            return new ResponseEntity(ErrorMessages.InvalidEmailId, HttpStatus.CONFLICT);
        }

        String otp = authService.generateOTP(requestBody.getUsername());
        //TODO need to store this otp in redis

        emailService.sendSimpleMessage(requestBody.getEmail(),
                SimpleMessageStrings.SignUpOTPSubject, SimpleMessageStrings.SignUpOTPText + otp);

        return new ResponseEntity(HttpStatus.OK);
    }


}
