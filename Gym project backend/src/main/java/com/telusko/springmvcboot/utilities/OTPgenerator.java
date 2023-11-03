package com.telusko.springmvcboot.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OTPgenerator {

    //will generate 4 digit otp
    public static String generateOTP() {
        Random rnd = new Random();
        // 0 - 999
        String otp = String.valueOf(rnd.nextInt(10000));
        if (otp.length() < 4) {

            otp = String.format("%4s", otp).replace(' ', '0');
        }

        return otp;
    }

}
