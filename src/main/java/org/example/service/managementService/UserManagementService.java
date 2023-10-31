package org.example.service.managementService;


import org.example.dto.RegisterFormDto;
import org.example.dto.UserUpdateDto;
import org.example.model.User;

public interface UserManagementService  {
    public String authenticateToken(String authorizationHeader);
    public void authenticate(String username, String password) throws Exception;
    public User fillFields(RegisterFormDto registerFormDto);
    public User updateFields(UserUpdateDto userUpdateDto, String authorizationHeader);
}
