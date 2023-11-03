package com.telusko.springmvcboot.security.exception;

import com.telusko.springmvcboot.utilities.constants.ErrorMessages;

//this is a custom exception class.
public class GymException extends RuntimeException{
    public GymException(String msg){
        super(msg);
    }
    public GymException(ErrorMessages msg){
        super(msg.getErrorMsg());
    }
}
