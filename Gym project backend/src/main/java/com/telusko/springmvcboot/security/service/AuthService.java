package com.telusko.springmvcboot.security.service;

import com.telusko.springmvcboot.security.JwtProvider;
import com.telusko.springmvcboot.security.dto.LoginRequest;
import com.telusko.springmvcboot.security.dto.RegisterRequest;
import com.telusko.springmvcboot.security.entity.User;
import com.telusko.springmvcboot.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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

    public boolean signUp(RegisterRequest registerRequest){
        if(isUserNameExistAlready(registerRequest.getUsername())){
            return false;
        }

        User user= new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEmail(  registerRequest.getEmail());

        userRepository.save(user);

        return true;
    }

    private boolean isUserNameExistAlready(String username) {
       return userRepository.findByusername(username).isPresent();
    }

    //below function will encode the password for a user before storing it in DB
    private String  encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) throws Exception {

        //returning a fully populated Authentication object (including granted authorities) if successful.
        try{
        Authentication authenticate=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (loginRequest.getUsername(),loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            String authenticationToken = jwtProvider.generateToken(authenticate);
            return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
        }
        catch (Exception e){
            throw new Exception("Invalid User");
        }



    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}
