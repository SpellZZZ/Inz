package org.example.controller;

import org.example.dto.AuthenticateResponseDto;
import org.example.dto.AuthenticationRequestDto;
import org.example.dto.RegisterFormDto;
import org.example.dto.UserUpdateDto;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.model.Commission;
import org.example.model.Role;
import org.example.model.Trailer;
import org.example.model.User;
import org.example.service.JwtAuthService;
import org.example.service.dbService.CommissionDBService;
import org.example.service.dbService.UserDBService;
import org.example.service.managementService.UserManagementService;
import org.example.util.JwtTokenUtil;
import org.example.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/user")
public class UserController {

    private final UserManagementService userManagementService;
    private final UserDBService userDBService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtAuthService jwtAuthService;
    private final CommissionDBService commissionDBService;

    @Autowired
    public UserController(UserManagementService userManagementService,
                          UserDBService userDBService,
                          JwtTokenUtil jwtTokenUtil,
                          JwtAuthService jwtAuthService,
                          CommissionDBService commissionDBService) {
        this.userManagementService = userManagementService;
        this.userDBService = userDBService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtAuthService = jwtAuthService;
        this.commissionDBService = commissionDBService;
    }

    @GetMapping("/allUsers")
    public List<User> getUsers() {
        return userDBService.getUsers();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponseDto> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequest) throws Exception {
        jwtAuthService.authenticate(authenticationRequest.getLoginUsername(), authenticationRequest.getLoginPassword());
        final String token = jwtTokenUtil.generateToken(authenticationRequest.getLoginUsername());
        final String role = userManagementService.getUserRole(authenticationRequest.getLoginUsername());
        final boolean status = userDBService.getUserByUserName(authenticationRequest.getLoginUsername()).getDeleted();
        return ResponseEntity.ok(new AuthenticateResponseDto(token, role, status));
    }

    @PostMapping("/userRegister")
    public ResponseEntity<Object> userRegister(@RequestBody RegisterFormDto registerFormDto) {
        try {
            validateUserNotExists(registerFormDto);
            User user = createUserFromDto(registerFormDto);
            userDBService.saveUser(user);
            return ResponseHelper.createSuccessResponse("Udało się dodać użytkownika.");
        } catch (ObjectAlreadyExistsException e) {
            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Wystąpił błąd podczas rejestracji użytkownika.");
        }
    }

    private void validateUserNotExists(RegisterFormDto registerFormDto) {
        if (userDBService.getUserByUserName(registerFormDto.getRegisterUsername()) != null ||
                userDBService.getUserByEmail(registerFormDto.getRegisterEmail()) != null) {
            throw new ObjectAlreadyExistsException("Użytkownik o takim loginie lub emailu już istnieje.");
        }
    }

    private User createUserFromDto(RegisterFormDto registerFormDto) {
        return userManagementService.fillFields(registerFormDto);
    }

    @PostMapping("/userUpdate")
    public ResponseEntity<Object> userUpdate(@RequestBody UserUpdateDto userUpdateDto,
                                             @RequestHeader("Authorization") String authorizationHeader) {
        try {
            User user = updateUserFields(userUpdateDto, authorizationHeader);
            userDBService.userUpdate(user);
            return ResponseHelper.createSuccessResponse("Zaktualizowano dane użytkownika");
        } catch (JwtTokenException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (Exception ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Wystąpił błąd");
        }
    }

    private User updateUserFields(UserUpdateDto userUpdateDto, String authorizationHeader) {
        final String token = jwtAuthService.authenticateToken(authorizationHeader);
        if (token == null) {
            throw new JwtTokenException("Wystąpił błąd z tokenem");
        }
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        User user = userManagementService.updateFields(userUpdateDto, authorizationHeader);
        System.out.println("test5");

        return user;
    }






    @PostMapping("/userRole")
    public Role userRole(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
        Role role = user.getRole();
        return role;
    }

    @GetMapping("/getDrivers")
    public ResponseEntity<List<User>> getDrivers(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        List<User> res = userDBService.getUserByCompany(user.getCompany()).stream().filter(
                x -> x.getRole().getRole_name().equals("Kierowca") )
                .collect(Collectors.toList());;

        return ResponseEntity.ok(res);
    }


    @PostMapping("/deactivateAccount")
    public ResponseEntity<Object> deactivateAccount(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);


        for(Commission c : commissionDBService.getCommissionByUser(user)){
            if( c.getCanceled() == 0 ){
                if(!(c.getIs_loaded() && c.getIs_unloaded())) return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Wystąpił błąd");
            }
        }

        user.setDeleted(true);
        userDBService.saveUser(user);

        return ResponseHelper.createSuccessResponse("Sukces");


    }






}