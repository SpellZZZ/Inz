package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class CompanyAddUserDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private String userLogin;


    public CompanyAddUserDto() {
    }

    public CompanyAddUserDto(String userLogin) {
        this.userLogin = userLogin;
    }

}
