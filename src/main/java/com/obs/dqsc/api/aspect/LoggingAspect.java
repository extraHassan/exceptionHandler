package com.obs.dqsc.api.aspect;

import com.obs.dqsc.api.exception.ErrorCode;
import com.obs.dqsc.api.exception.ErrorMessage;
import com.obs.dqsc.api.exception.technical.LoggingAspectThrowableException;
import io.swagger.v3.oas.annotations.Hidden;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author Hassan EL MZABI
 * @implNote Aspect logging class to log execution of method, before and after execution, in a generic way
 * @since 11-11-2021
 */
@Aspect
@Component
@Controller
@Hidden
@RequestMapping("no root found")
public class LoggingAspect  {

    /**
     * @param joinPoint execution of a method (before and after)
     * @return proceed operation
     * @throws LoggingAspectThrowableException
     */
    @Around("com.obs.dqsc.api.config.AspectConfig.allLayers()")
    public Object logMethod(final ProceedingJoinPoint joinPoint) throws LoggingAspectThrowableException {
        final Class<?> targetClass = joinPoint.getTarget().getClass();
        final Logger logger = LoggerFactory.getLogger(targetClass);
        try {
            final String className = targetClass.getSimpleName();
            logger.info(getPreMessage(joinPoint, className));

            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            final Object retVal = joinPoint.proceed();
            stopWatch.stop();
            logger.info(getPostMessage(joinPoint, className, stopWatch.getTotalTimeMillis()));
            return retVal;
        } catch (final Throwable ex) {
            logger.error(ex.getMessage());

            return throwException(new LoggingAspectThrowableException(ex.getMessage()));
        }
    }

    /**
     * @param ex Exception elevated while proceeding the joinPoint
     * @return message to the client, showing what went wrong
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> throwException(Exception ex)  {
        ErrorMessage message = getErrorMessage(ex);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @param ex Exception to get message from
     * @return well formatted message of Type ErrorMessage
     */
    private ErrorMessage getErrorMessage(Exception ex) {
        return new ErrorMessage(
                ErrorCode.ASPECT_LOGGING_EXCEPTION,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                null);
    }

    private static String getPreMessage(final JoinPoint joinPoint, final String className) {

        final StringBuilder builder = new StringBuilder()
                .append("Entered in ").append(className).append(".")
                .append(joinPoint.getSignature().getName())
                .append("(");
        appendTo(builder, joinPoint);
        return builder
                .append(")")
                .toString();
    }

    private static String getPostMessage(final JoinPoint joinPoint, final String className, final long millis) {
        return "Exit from " + className + "." +
                joinPoint.getSignature().getName() +
                "(..); Execution time: " +
                millis +
                " ms;"
                ;
    }

    private static void appendTo(final StringBuilder builder, final JoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(args[i]);
        }
    }

}
