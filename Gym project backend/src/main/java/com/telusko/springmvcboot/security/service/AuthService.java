package com.telusko.springmvcboot.security.service;

import com.telusko.springmvcboot.security.JwtProvider;
import com.telusko.springmvcboot.security.dto.LoginRequest;
import com.telusko.springmvcboot.security.dto.RegisterRequest;
import com.telusko.springmvcboot.security.entity.User;
import com.telusko.springmvcboot.utilities.OTPgenerator;
import com.telusko.springmvcboot.utilities.constants.ErrorMessages;
import com.telusko.springmvcboot.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    // this bean is created from SecurityConfig
    private PasswordEncoder passwordEncoder;


    @Autowired
    // this bean is created from SecurityConfig
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public ErrorMessages signUp(RegisterRequest registerRequest) {
        String cacheKey= "otp::"+registerRequest.getUsername();
        String storedOTP=redisTemplate.opsForValue().get(cacheKey);

        if(!storedOTP.equals("\""+registerRequest.getOtp()+"\"")){
            log.debug(getClass().getName()+" OTP verification Failed!!");

            if(storedOTP.equals("")) return ErrorMessages.OTPExpired;

            return ErrorMessages.WrongOTP;
        }

        //after sucessful verification removing cache from redis
        redisTemplate.delete(cacheKey);

        ErrorMessages result = validate(registerRequest);

        if (result != ErrorMessages.NoError) {
            log.debug(getClass().getName() + "In Singup function, Entered data has some duplicate properties");
            return ErrorMessages.UserOrEmailExist;
        }

        //TODO OTP needs to validated against cahced one after validation evict it. also do setup expiration

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());

        userRepository.save(user);

        return ErrorMessages.NoError;
    }


    /*
     for sing-up: checks if username or email already exist
     for forgot password: checks it email exits
    * */
    public ErrorMessages validate(RegisterRequest requestBody) {


        if (userRepository.findByusername(requestBody.getUsername()).isPresent()) {
            log.info(getClass().getName() + " Entered username already exist in system");
            return ErrorMessages.UserExist;
        }
        if (userRepository.findByemail(requestBody.getEmail()).isPresent()) {
            log.info(getClass().getName() + " Entered email already exist in system");
            return ErrorMessages.EmailExist;
        }

        return ErrorMessages.NoError;
    }

    //below function will encode the password for a user before storing it in DB
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) throws Exception {

        //returning a fully populated Authentication object (including granted authorities) if successful.
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken
                            (loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            String authenticationToken = jwtProvider.generateToken(authenticate);
            return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
        } catch (Exception e) {
            throw new Exception("Invalid User");
        }


    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

    @Cacheable(value="otp")
    public String generateOTP(String userName){
        return OTPgenerator.generateOTP();
    }

}
