package org.example.service.managementService;

import org.example.service.dbService.UserDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserJwtDetailsService implements UserDetailsService {

    @Autowired
    private UserDBService userDBService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        org.example.model.User user = userDBService.getUserByUserName(login);
        if(user == null) throw new UsernameNotFoundException("User not found with username: " + login);
        System.out.println(user.getPassword());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());

    }






}