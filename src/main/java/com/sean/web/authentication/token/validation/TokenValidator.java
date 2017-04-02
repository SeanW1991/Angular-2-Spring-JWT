package com.sean.web.authentication.token.validation;


public interface TokenValidator {
    public String validate(String payload);
}
