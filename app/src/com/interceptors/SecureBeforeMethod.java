package com.interceptors;

import com.exception.AccessLevelException;
import com.model.RoleId;
import com.model.User;
import com.services.AuthService;
import com.utils.request.Secured;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class SecureBeforeMethod {

    @Autowired
    AuthService authService;

    @Around("@annotation(com.utils.request.Secured)")
    public String doSecure(ProceedingJoinPoint joinPoint) throws AccessLevelException {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

        RoleId needRole = method.getAnnotation(Secured.class).value();
        User loggedUser;
        if ((loggedUser = authService.getLoggedUser()) != null) {
            if (loggedUser.getRoleId() == needRole) {
                try {
                    return (String) joinPoint.proceed();
                } catch (Throwable exc) {}
            }
        }

        throw new AccessLevelException();
    }
}
