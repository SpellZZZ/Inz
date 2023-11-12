package org.example.dto;

import java.io.Serializable;

public class AuthenticationRequestDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String loginUsername;
    private String loginPassword;

    //need default constructor for JSON Parsing
    public AuthenticationRequestDto() {

    }

    public AuthenticationRequestDto(String username, String password) {
        this.setLoginUsername(username);
        this.setLoginPassword(password);
    }

    public String getLoginUsername() {
        return this.loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return this.loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

}
/*
public class AuthenticationRequestDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    User user;


    public AuthenticationRequestDto() {}

    public AuthenticationRequestDto(String login, String password) {
        this.setLogin(login);
        this.setPassword(password);
    }


    public void setUser(User user){
        this.user = user;
    }
    public String getLogin() {
        return this.user.getLogin();
    }

    public void setLogin(String login) {
        this.user.setLogin(login);

    }

    public String getPassword() {
        return this.user.getPassword();
    }

    public void setPassword(String password) {
        this.user.setPassword(password);
    }

}*/