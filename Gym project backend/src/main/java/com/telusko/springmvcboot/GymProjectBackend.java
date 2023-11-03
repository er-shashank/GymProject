package com.telusko.springmvcboot;

import com.telusko.springmvcboot.security.service.EmailServiceImpl;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableEncryptableProperties
public class GymProjectBackend {
	public static void main(String[] args) {
		SpringApplication.run(GymProjectBackend.class, args);
	}
}
