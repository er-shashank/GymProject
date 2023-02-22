//package com.telusko.springmvcboot;
//
//import java.util.List;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.telusko.springmvcboot.model.Alien;
//
//@Aspect
//@Component
//public class LoggingAspect {
//	public static final Logger LOGGER=  LoggerFactory.getLogger(LoggingAspect.class);
//	@Before("execution (public * com.telusko.springmvcboot.AlienController.getAlienByName())")
//	public void log() {
//		LOGGER.debug("Log method is called!!!!!!!!!!!!!!!!!");
//	}
//}
