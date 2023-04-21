package com.myezen.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SampleAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	@Before("execution(* com.myezen.myapp.service.BoardService*.*(..))")
	public void startLog() {
		
		logger.info("------------------");
		logger.info("aop �α��׽�Ʈ���Դϴ�.");
		logger.info("------------------");
		System.out.println("sysout�Դϴ�.");
	}

}
