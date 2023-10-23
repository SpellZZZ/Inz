package org.example.controller;

import org.example.dto.RegisterFormDto;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("/user")
public class UseController {

    @Autowired
    private UserService userService;

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
    @PostMapping("/userRegister")
    public void userRegister(@RequestBody RegisterFormDto registerFormDto){
        System.out.println("asd");
        System.out.println(registerFormDto.getRegisterEmail());

        System.out.println(registerFormDto.getRegisterPassword());
        String hashedPassword = passwordEncoder.encode(registerFormDto.getRegisterPassword());
        System.out.println(hashedPassword);

        User user = new User();
        user.setUsername(registerFormDto.getRegisterUsername());
        user.setPassword(hashedPassword);
        user.setEmail(registerFormDto.getRegisterEmail());
        //user.setUsername(registerFormDto.getRegisterUsername());
        //user.setUsername(registerFormDto.getRegisterUsername());
        user.setRole(null);
        user.setDeleted("no");
        user.setCompany(null);
        user.setPassword_recovery_link("123");

        userService.saveUser(user);
    }


}
