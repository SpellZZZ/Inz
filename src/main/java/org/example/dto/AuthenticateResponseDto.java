package org.example.dto;

import java.io.Serializable;

public class AuthenticateResponseDto implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String accessToken;
    public AuthenticateResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken() {
        return this.accessToken;
    }

}