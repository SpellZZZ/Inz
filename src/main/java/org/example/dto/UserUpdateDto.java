package org.example.dto;

import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;


@Getter
@Setter
public class UserUpdateDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private  String name;
    private  String surname;
    private  String email;
    private  String password;

    public UserUpdateDto() {
    }


    public UserUpdateDto(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
