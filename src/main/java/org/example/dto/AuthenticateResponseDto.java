package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthenticateResponseDto implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;
    private final String role;
    private final boolean cancaled;
    public AuthenticateResponseDto(String accessToken, String role, boolean cancaled) {
        this.token = accessToken;
        this.role = role;
        this.cancaled = cancaled;
    }



}