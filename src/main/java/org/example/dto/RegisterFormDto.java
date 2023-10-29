package org.example.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RegisterFormDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private  String registerUsername;
    private  String registerPassword;
    private  String registerEmail;
    private  String registerName;
    private  String registerSurname;

    public RegisterFormDto() {
    }

    public RegisterFormDto(String registerUsername, String registerPassword, String registerEmail, String registerName, String registerSurname) {
        this.registerUsername = registerUsername;
        this.registerPassword = registerPassword;
        this.registerEmail = registerEmail;
        this.registerName = registerName;
        this.registerSurname = registerSurname;
    }




}