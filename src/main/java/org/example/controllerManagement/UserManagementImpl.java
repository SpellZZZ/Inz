package org.example.controllerManagement;

import org.example.dto.RegisterFormDto;
import org.example.dto.UserUpdateDto;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserManagementImpl implements UserManagement{


    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public String authenticateToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") ) return authorizationHeader.substring(7);
        return null;
    }


    //obsluzyc to
    @Override
    public void authenticate(String username, String password)  {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Override
    public User fillUserFields(RegisterFormDto registerFormDto) {
        return null;
    }

    @Override
    public User updateUserFields(UserUpdateDto userUpdateDto) {
        return null;
    }
}
