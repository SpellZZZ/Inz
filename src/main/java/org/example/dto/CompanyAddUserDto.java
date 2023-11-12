package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class CompanyAddUserDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private String login;


    public CompanyAddUserDto() {
    }

    public CompanyAddUserDto(String login) {
        this.login = login;
    }

}
