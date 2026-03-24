package com.ERP.backend.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspects {
	
	// @Before --> Action
	// execution --> joinPoint
	// Cross cutting concern implementation
	@Around("execution(* com.ERP.backend.Service.Impl.*.*(..))")
	public Object logBeforeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		log.info("Execution Begins : " + joinPoint.getSignature());
		
		Object result = joinPoint.proceed();
		
		long endTime = System.currentTimeMillis();
		log.info("Execution ends : " + joinPoint.getSignature() + " -- Time taken {} ms", endTime - startTime );
		
		return result;
	}
}
