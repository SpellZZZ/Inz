package org.example.dto;

import java.io.Serializable;

public class AuthenticateResponseDto implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String accessToken;
    private final String role;
    public AuthenticateResponseDto(String accessToken, String role) {
        this.accessToken = accessToken;
        this.role = role;
    }

    public String getToken() {
        return this.accessToken;
    }


    public String getRole() {
        return this.role;
    }

}