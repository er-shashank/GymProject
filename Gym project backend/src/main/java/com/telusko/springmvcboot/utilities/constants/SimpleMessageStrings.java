package com.telusko.springmvcboot.utilities.constants;

public enum SimpleMessageStrings {
    SignUpOTPSubject("Your One Time Password is Here"),
    SignUpOTPText("Your One Time Password is Here");

    private final String text;

    SimpleMessageStrings(String text) {
        this.text = text;
    }
    public String getText(){ return this.text;}
}
