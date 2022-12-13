package com.interns.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.interns.exception.InternException;


@Aspect
@Component
public class LoggingAspect
{

    private static final Log LOGGER = LogFactory.getLog(LoggingAspect.class);
    
    @AfterThrowing(pointcut = "execution(com.infy.infyinterns.service.*Impl.*(..))", throwing = "exception")
    public void logServiceException(InternException exception)
    {
    	LOGGER.error(exception.getMessage(), exception);
	// code
    }

}
