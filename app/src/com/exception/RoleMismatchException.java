package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class RoleMismatchException extends RuntimeException {
    public RoleMismatchException() {
        super("Вашей группе запрещено выполнять данное действие!");
    }
}
