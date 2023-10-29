package org.example.controller;

import org.example.dto.RegisterFormDto;
import org.example.dto.UserUpdateDto;
import org.example.model.User;
import org.example.service.UserService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/allUsers")
    List<User> getUsers(){
        return userService.getUsers();
    }
    @GetMapping("/userByLogin")
    List<User> getUsersByLogin(){
        List<User> u = new ArrayList<>();
        u.add(userService.getUserByUserName("foolishuser"));
        return u;
    }
    //duzo do edycji
    //dodanie wyjatku
    @PostMapping("/userRegister")
    public ResponseEntity<Object> userRegister(@RequestBody RegisterFormDto registerFormDto){

        String hashedPassword = passwordEncoder.encode(registerFormDto.getRegisterPassword());

        User user = new User();
        user.setUsername(registerFormDto.getRegisterUsername());
        user.setPassword(hashedPassword);
        user.setEmail(registerFormDto.getRegisterEmail());
        user.setRole(null);
        user.setDeleted("no");
        user.setCompany(null);
        user.setPassword_recovery_link("123");

        userService.saveUser(user);

        Map<String, Object> response = new HashMap<>();
        response.put("validate", true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //dodac jeszcze cos tu
    @PostMapping("/userUpdate")
    public ResponseEntity<String> userUpdate(@RequestBody UserUpdateDto userUpdateDto,
                                             @RequestHeader("Authorization") String authorizationHeader) {
        String token;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Brak autoryzacji");
        }

        String userName = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.getUserByUserName(userName);

        user.setEmail( userUpdateDto.getEmail() );
        String hashedPassword = passwordEncoder.encode(userUpdateDto.getPassword());
        user.setPassword(hashedPassword);

        userService.userUpdate(user);


        return ResponseEntity.ok("Zaktualizowano dane użytkownika");

    }


}
