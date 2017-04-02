package com.sean.web.model.token;

public final class JwtToken implements Token {

    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return this.token;
    }
}
