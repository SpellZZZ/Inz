package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthenticateResponseDto implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private  String token;
    private  String role;
    private  boolean cancaled;
    public AuthenticateResponseDto() {

    }

    public AuthenticateResponseDto(String token, String role, boolean cancaled) {
        this.token = token;
        this.role = role;
        this.cancaled = cancaled;
    }



}