package org.example.service.managementService;


import org.example.dto.RegisterFormDto;
import org.example.dto.UserUpdateDto;
import org.example.model.User;

public interface UserManagementService  {
    public User fillFields(RegisterFormDto registerFormDto);
    public User updateFields(UserUpdateDto userUpdateDto, String authorizationHeader);
    public String getUserRole(String userName);
    public User getUserByToken(String token);
}
