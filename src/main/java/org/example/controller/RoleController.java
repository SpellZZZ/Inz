package org.example.controller;


import org.example.model.Role;
import org.example.model.User;
import org.example.service.dbService.CompanyDBService;
import org.example.service.dbService.RoleDBService;
import org.example.service.dbService.UserDBService;
import org.example.service.managementService.CompanyManagementService;
import org.example.service.managementService.UserManagementService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    private final RoleDBService roleDBService;

    @Autowired
    public RoleController(RoleDBService roleDBService) {
        this.roleDBService = roleDBService;
    }


    @GetMapping("/allRoles")
    public List<Role> getRoles() {
        return roleDBService.getRole();
    }


}
