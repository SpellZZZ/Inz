package org.example.service.managementService;


import org.example.dto.RegisterFormDto;
import org.example.dto.UserUpdateDto;
import org.example.exceptions.JwtTokenException;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.JwtAuthService;
import org.example.service.dbService.UserDBService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserManagementServiceImpl implements UserManagementService {


    private final UserDBService userDBService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtAuthService jwtAuthService;


    @Autowired
    public UserManagementServiceImpl(

            UserDBService userDBService,
            JwtTokenUtil jwtTokenUtil,
            JwtAuthService jwtAuthService
    ) {

        this.userDBService = userDBService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtAuthService = jwtAuthService;

    }

    @Override
    public User fillFields(RegisterFormDto registerFormDto) {
        String hashedPassword = jwtAuthService.hashedPassword(registerFormDto.getRegisterPassword());

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


    //todo sprawdz czy mail istniejes
    @Override
    public User updateFields(UserUpdateDto userUpdateDto, String authorizationHeader) {

        final String token = jwtAuthService.authenticateToken(authorizationHeader);

        if (token == null) {
            throw new JwtTokenException("Wystąpił błąd z tokenem");
        }

        String userName = jwtTokenUtil.getUsernameFromToken(token);
        User user = userDBService.getUserByUserName(userName);

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPassword() != null) {
            String hashedPassword = jwtAuthService.hashedPassword(userUpdateDto.getPassword());
            user.setPassword(hashedPassword);
        }
        return user;
    }

    @Override
    public String getUserRole(String userName) {
        User user = userDBService.getUserByUserName(userName);
        Role userRole = user.getRole();
        return userRole == null ? "" : userRole.getRole_name();
    }
}