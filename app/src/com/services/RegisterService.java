package com.services;

import com.utils.request.validator.RequestValidationChain;


public interface RegisterService {
    boolean register(RequestValidationChain requestValidationChain);

    boolean checkEmptyLogin(String login);
}
