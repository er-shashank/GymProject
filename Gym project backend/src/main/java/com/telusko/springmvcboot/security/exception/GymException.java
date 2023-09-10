package com.telusko.springmvcboot.security.exception;

//this is a custom exception class.
public class GymException extends RuntimeException{
    public GymException(String msg){
        super(msg);
    }
}
