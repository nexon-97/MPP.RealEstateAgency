package com.security;

import com.exception.AccessLevelException;
import com.exception.RoleMismatchException;
import com.exception.UnauthorizedException;
import com.model.RoleId;
import com.model.User;
import com.security.annotations.AccessLevelCheck;
import com.security.annotations.RoleCheck;
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
public class SecurityAnnotationsHandler {

    @Autowired
    AuthService authService;

    @Around("@annotation(com.security.annotations.AccessLevelCheck)")
    public String doCheckAccessLevel(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

        AccessLevel level = method.getAnnotation(AccessLevelCheck.class).value();
        User loggedUser;
        if ((loggedUser = authService.getLoggedUser()) != null) {
            int userAccessLevel = level.getValueForRole(loggedUser.getRoleId());
            if (userAccessLevel >= level.getValue()) {
                try {
                    return (String) joinPoint.proceed();
                } catch (Throwable ex) {
                    throw ex;
                }
            }
        }

        throw new AccessLevelException();
    }

    @Around("@annotation(com.security.annotations.AuthCheck)")
    public String doCheckAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        if (authService.isUserLoggedIn()) {
            try {
                return (String) joinPoint.proceed();
            } catch (Throwable ex) {
                throw ex;
            }
        }

        throw new UnauthorizedException();
    }

    @Around("@annotation(com.security.annotations.RoleCheck)")
    public String doCheckRole(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        RoleId role = method.getAnnotation(RoleCheck.class).value();

        if (authService.isUserLoggedIn() && authService.getLoggedUser().getRoleId() == role) {
            try {
                return (String) joinPoint.proceed();
            } catch (Throwable ex) {
                throw ex;
            }
        }

        throw new RoleMismatchException();
    }
}
