package org.example.service.managementService;


import org.example.dto.RegisterFormDto;
import org.example.dto.UserUpdateDto;
import org.example.exceptions.JwtTokenException;
import org.example.model.User;
import org.example.service.dbService.UserDBService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDBService userDBService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserManagementServiceImpl(
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserDBService userDBService,
            JwtTokenUtil jwtTokenUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDBService = userDBService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public String authenticateToken(String authorizationHeader) {
        return (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) ? authorizationHeader.substring(7) : null;
    }

    @Override
    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Override
    public User fillFields(RegisterFormDto registerFormDto) {
        String hashedPassword = passwordEncoder.encode(registerFormDto.getRegisterPassword());
        User user = new User();
        user.setUsername(registerFormDto.getRegisterUsername());
        user.setPassword(hashedPassword);
        user.setEmail(registerFormDto.getRegisterEmail());
        user.setRole(null);
        user.setDeleted(false);
        user.setCompany(null);
        user.setPassword_recovery_link("123");
        return user;
    }

    @Override
    public User updateFields(UserUpdateDto userUpdateDto, String authorizationHeader) {
        final String token = authenticateToken(authorizationHeader);
        if (token == null) {
            throw new JwtTokenException("Wystąpił błąd z tokenem");
        }

        String userName = jwtTokenUtil.getUsernameFromToken(token);
        User user = userDBService.getUserByUserName(userName);

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPassword() != null) {
            String hashedPassword = passwordEncoder.encode(userUpdateDto.getPassword());
            user.setPassword(hashedPassword);
        }
        return user;
    }
}