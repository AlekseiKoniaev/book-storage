//package ru.koniaev.bookstorage.logger;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.SourceLocation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Type;
//import java.util.Arrays;
//import java.util.StringJoiner;
//import java.util.stream.Collectors;
//
//@Aspect
//@Component
//public class ControllerLogger {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Pointcut("execution(public * ru.koniaev.bookstorage.service.EntityService.insert(..))")
//    public void callInsert() {}
//
//    @AfterReturning(value = "callInsert()", returning = "returningValue")
//    public void logSuccessfulInsert(JoinPoint joinPoint, boolean returningValue) {
//
//        StringJoiner joiner = new StringJoiner(",");
//        for (Object arr : joinPoint.getArgs()) {
//            for (Object obj : (Object[]) arr) {
//                String toString = obj.toString();
//                joiner.add(toString);
//            }
//        }
//        String args = joiner.toString();
//
//        String entity = joinPoint.getSourceLocation().getWithinType().getGenericInterfaces()[0].getTypeName();
//
//        logger.info("POST {} ({}) {}", entity, args, returningValue ? "SUCCESS" : "FAILED");
//    }
//}
