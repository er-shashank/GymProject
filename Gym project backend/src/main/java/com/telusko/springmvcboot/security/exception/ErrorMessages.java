package com.telusko.springmvcboot.security.exception;

public enum ErrorMessages {
    UserExist("User Already "),
    NoUserLoggedIn("No User is Logged In!!!"),

    NoGymPlan("You have not created any plan yet!!")
    ;

    private final String errorMsg;
    ErrorMessages(String errorMsg){
        this.errorMsg= errorMsg;
    }

    public String getErrorMsg(){
        return this.errorMsg;
    }
}
