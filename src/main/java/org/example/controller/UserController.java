package org.example.controller;

import org.example.dto.AuthenticateResponseDto;
import org.example.dto.AuthenticationRequestDto;
import org.example.dto.RegisterFormDto;
import org.example.dto.UserUpdateDto;
import org.example.exceptions.DatabaseSaveException;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.model.User;
import org.example.service.UserService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/user")
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/allUsers")
    List<User> getUsers(){
        return userService.getUsers();
    }


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getLoginUsername(), authenticationRequest.getLoginPassword());
        final String token = jwtTokenUtil.generateToken(authenticationRequest.getLoginUsername());
        return ResponseEntity.ok(new AuthenticateResponseDto(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public String authenticateToken(String authorizationHeader){

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") ) return authorizationHeader.substring(7);
        return null;
    }



    //TODO duzo do edycji - kiedy beda dodawne nowe pola do bazy danych trzeba to tu uwzglednic
    @PostMapping("/userRegister")
    public ResponseEntity<Object> userRegister(@RequestBody RegisterFormDto registerFormDto) {

        Map<String, Object> response = new HashMap<>();

        try {
            String hashedPassword = passwordEncoder.encode(registerFormDto.getRegisterPassword());

            if( userService.getUserByUserName(registerFormDto.getRegisterUsername()) != null ||
                    userService.getUserByEmail(registerFormDto.getRegisterEmail()) != null
            ) throw new UserAlreadyExistsException("Uzytkownik o takim loginie lub emailu istneije");

            User user = new User();
            user.setUsername(registerFormDto.getRegisterUsername());
            user.setPassword(hashedPassword);
            user.setEmail(registerFormDto.getRegisterEmail());
            user.setRole(null);
            user.setDeleted(false);
            user.setCompany(null);
            user.setPassword_recovery_link("123");

            userService.saveUser(user);

            response.put("validate", true);
            response.put("message", "Udalo sie dodac uzytkownika do bazy danych");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (UserAlreadyExistsException e) {
            response.put("validate", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 Konflikt
        } /*catch (DatabaseSaveException e) {
            response.put("validate", false);
            response.put("message", "Wystąpił błąd z baza danych");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        } */catch (Exception e) {
            response.put("validate", false);
            response.put("message", "Wystąpił błąd podczas rejestracji użytkownika");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }


    }


    //TODO dodac jeszcze cos tu - chodzi o pola user
    @PostMapping("/userUpdate")
    public ResponseEntity<Object> userUpdate(@RequestBody UserUpdateDto userUpdateDto,
                                             @RequestHeader("Authorization") String authorizationHeader) {


        Map<String, Object> response = new HashMap<>();


        try {
            final String token = authenticateToken(authorizationHeader);
            if(token == null) {
                throw new JwtTokenException("Wystapil blad z tokenem");

            }


            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.getUserByUserName(userName);

            if(userUpdateDto.getEmail() != null) {
                user.setEmail( userUpdateDto.getEmail() );
            }
            if(userUpdateDto.getPassword() != null) {
                String hashedPassword = passwordEncoder.encode(userUpdateDto.getPassword());
                user.setPassword(hashedPassword);
            }

            userService.userUpdate(user);

            response.put("validate", true);
            response.put("message", "Zaktualizowano dane użytkownika");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JwtTokenException ex) {
            response.put("validate", false);
            response.put("message", ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); //401
        }
        catch (Exception ex) {
            response.put("validate", false);
            response.put("message", "Wystapil blad");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }



    }


}
