package com.telusko.springmvcboot.security.dto;

//this class is used for New user registration
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String otp;

    public String getOtp() {
        return otp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
