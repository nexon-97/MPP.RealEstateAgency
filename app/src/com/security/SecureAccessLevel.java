package com.security;

import com.exception.AccessLevelException;
import com.exception.UnauthorizedException;
import com.model.User;
import com.services.AuthService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class SecureAccessLevel {

    @Autowired
    AuthService authService;

    @Around("@annotation(com.security.AccessLevelCheck)")
    public String doSecure(ProceedingJoinPoint joinPoint) throws AccessLevelException {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

        AccessLevel level = method.getAnnotation(AccessLevelCheck.class).value();
        User loggedUser;
        if ((loggedUser = authService.getLoggedUser()) != null) {
            int userAccessLevel = level.getValueForRole(loggedUser.getRoleId());
            if (userAccessLevel >= level.getValue()) {
                try {
                    return (String) joinPoint.proceed();
                } catch (Throwable exc) {}
            }
        }

        throw new AccessLevelException();
    }

    @Around("@annotation(com.security.AuthCheck)")
    public String doCheckAuth(ProceedingJoinPoint joinPoint) throws UnauthorizedException {
        if (authService.isUserLoggedIn()) {
            try {
                return (String) joinPoint.proceed();
            } catch (Throwable exc) {}
        }

        throw new UnauthorizedException();
    }
}
