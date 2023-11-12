package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CompanyUserSetRoleDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private  String role;
    private  String login;


    public CompanyUserSetRoleDto() {
    }

    public CompanyUserSetRoleDto(String role, String login) {
        this.role = role;
        this.login = login;
    }
}