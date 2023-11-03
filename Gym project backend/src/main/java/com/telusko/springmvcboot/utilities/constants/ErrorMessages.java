package com.telusko.springmvcboot.utilities.constants;

import org.springframework.http.HttpStatus;

public enum ErrorMessages {
    UserExist("User Already Exist",HttpStatus.CONFLICT),
    EmailExist("Email Already Exist",HttpStatus.CONFLICT),
    UserOrEmailExist("User or Email Already Exist",HttpStatus.CONFLICT),
    NoUserLoggedIn("No User is Logged In!!!",HttpStatus.BAD_REQUEST),

    NoGymPlan("You have not created any plan yet!!",HttpStatus.NOT_FOUND),
    ReachedLimit("User has Reached the Limit!",HttpStatus.BAD_REQUEST),

    NoError("No error", HttpStatus.OK),
    InvalidEmailId("Entered Email Id is in invalid format", HttpStatus.BAD_REQUEST),
    WrongOTP("Entered OTP is wrong", HttpStatus.UNAUTHORIZED),

    OTPExpired("Entered OTP is Expired, please re-generate the otp", HttpStatus.UNAUTHORIZED)
    ;

    private final String errorMsg;
    private final HttpStatus status;
    ErrorMessages(String errorMsg, HttpStatus status){

        this.errorMsg= errorMsg;
        this.status=status;
    }

    public String getErrorMsg(){
        return this.errorMsg;
    }

    public HttpStatus getHttpStatus(){
        return this.status;
    }

}
