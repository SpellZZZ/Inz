package org.example.controllerManagement;

import org.example.dto.RegisterFormDto;
import org.example.dto.UserUpdateDto;
import org.example.model.User;
import org.springframework.http.ResponseEntity;

public interface UserManagement {
    public String authenticateToken(String authorizationHeader);
    public void authenticate(String username, String password);
    public User fillUserFields(RegisterFormDto registerFormDto);
    public User updateUserFields(UserUpdateDto userUpdateDto);

}
