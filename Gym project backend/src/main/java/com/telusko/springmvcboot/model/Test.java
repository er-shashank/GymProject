package com.telusko.springmvcboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Test {
    @Id 
    int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Test(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Test [x=" + x + "]";
    }
     
}
