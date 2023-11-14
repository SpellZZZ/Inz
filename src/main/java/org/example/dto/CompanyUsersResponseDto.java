package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CompanyUsersResponseDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private  String name;
    private  String surname;
    private  String login;
    private  String role;



    public CompanyUsersResponseDto() {
    }

    public CompanyUsersResponseDto(String name, String surname, String login, String role) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.role = role;
    }
}