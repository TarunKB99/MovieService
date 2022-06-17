package com.moviesvc.moviesvc;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class Logging {
	Logger log = LoggerFactory.getLogger(Logging.class);

	@Pointcut(value = "execution(* com.moviesvc.moviesvc.*.*.*(..) )")
	// execution(* io.db.DynamoDb..*.*(..) -->All pck classes with sub pkg clases
	public void myPointcut() {

	}

	@Around("myPointcut()")
	public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().toString();
		Object[] array = pjp.getArgs();
		log.info("method invoked " + className + " : " + methodName + "()" + "arguments : "
				+ mapper.writeValueAsString(array));
		Object object = pjp.proceed();
		log.info(className + " : " + methodName + "()" + "Response : " + mapper.writeValueAsString(object));
		return object;

	}

	@AfterThrowing(pointcut = "(myPointcut())", throwing = "e")
	public void logExceptions(JoinPoint pjp, Exception e) {

		log.error(e.getMessage(), e);
	}

}
