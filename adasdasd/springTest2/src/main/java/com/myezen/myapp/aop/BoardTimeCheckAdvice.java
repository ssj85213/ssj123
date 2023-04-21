package com.myezen.myapp.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BoardTimeCheckAdvice {

	private static final Logger logger = LoggerFactory.getLogger(BoardTimeCheckAdvice.class);
	
	@Around("execution(* com.myezen.myapp.service.BoardService*.*(..))")
	public Object timelog(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		
		logger.info("before AOP");
		logger.info(Arrays.toString(pjp.getArgs()));
		long startTime = System.currentTimeMillis();
		
		result = pjp.proceed();
		
		long endTime = System.currentTimeMillis();
		logger.info("After AOP");
		logger.info(pjp.getSignature().getName()+":"+(endTime-startTime));
		
		return result;
	}
	
	
	
	
}
