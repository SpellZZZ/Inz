package org.example.service.managementService;


import org.example.dto.CompanyAddUserDto;
import org.example.dto.CompanyFormDto;
import org.example.dto.CompanyUserSetRoleDto;
import org.example.exceptions.UserDoesntExistsException;
import org.example.model.*;
import org.example.service.JwtAuthService;
import org.example.service.dbService.*;
import org.example.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyManagementServiceImpl implements CompanyManagementService {

    private final CompanyDBService companyDBService;
    private final JwtAuthService jwtAuthService;
    private final UserManagementService userManagementService;
    private final RoleDBService roleDBService;
    private final UserDBService userDBService;
    private final UserTruckDBService userTruckDBService;

    @Autowired
    public CompanyManagementServiceImpl(CompanyDBService companyDBService,
                                        JwtAuthService jwtAuthService,
                                        UserManagementService userManagementService,
                                        RoleDBService roleDBService,
                                        UserDBService userDBService,
                                        RouteTruckDBService routeTruckDBService,
                                        UserTruckDBService userTruckDBService
    ) {
        this.companyDBService = companyDBService;
        this.jwtAuthService = jwtAuthService;
        this.userManagementService = userManagementService;
        this.roleDBService = roleDBService;
        this.userDBService = userDBService;
        this.userTruckDBService = userTruckDBService;
    }
    @Override
    public void setOwner(Company company, String authorizationHeader) {

        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        user.setCompany(company);
        Role role = roleDBService.getRoleByName("Wlasciciel");
        user.setRole(role);
        userDBService.userUpdate(user);


    }

    //todo pobrac nick, zapisac go w userze ktoryto tworzy
    @Override
    public Company fillFields(CompanyFormDto companyFormDto, String authorizationHeader) {

        User owner = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        Company company = new Company();
        company.setCompany_name(companyFormDto.getCompany_name());
        company.setCompany_nip(companyFormDto.getCompany_nip());
        return company;
    }

    @Override
    public Company updateFields(CompanyFormDto companyFormDto, String authorizationHeader) {

        User owner = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        return null;
    }

    @Override
    public void addNewUser(CompanyAddUserDto companyAddUserDto, String authorizationHeader) {

        User owner = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
        System.out.println(owner.getUsername());
        Company company = owner.getCompany();
        User user = userDBService.getUserByUserName(companyAddUserDto.getLogin());
        System.out.println(user.getUsername());
        System.out.println(companyAddUserDto.getLogin());

        if(user == null) {
            throw new UserDoesntExistsException("Nie ma takiego uzytkownika");
        }

        user.setCompany(company);
        userDBService.userUpdate(user);
    }

    @Override
    public void setUserRole(CompanyUserSetRoleDto companyUserSetRoleDto, String authorizationHeader) throws Exception {
        User user = userDBService.getUserByUserName(companyUserSetRoleDto.getLogin());

        if(user.getRole().getRole_name().equals("Kierowca")){
            List<User_Truck> userTrucksList = userTruckDBService.getUserTrucks();

            for(User_Truck x : userTrucksList) {
                if(x.getUser().getUser_id() == user.getUser_id()){
                    throw new Exception("Użytkownik ma przypisana ciezarowke");
                }

            }



        }

        Role role = roleDBService.getRoleByName(companyUserSetRoleDto.getRole());

        user.setRole(role);
        userDBService.userUpdate(user);

    }

    @Override
    public void delete(CompanyAddUserDto companyAddUserDto, String authorizationHeader) throws Exception{
        User user = userDBService.getUserByUserName(companyAddUserDto.getLogin());

        if(user.getRole().getRole_name().equals("Kierowca")){
            List<User_Truck> userTrucksList = userTruckDBService.getUserTrucks();

            for(User_Truck x : userTrucksList) {
                if(x.getUser().getUser_id() == user.getUser_id()){
                    throw new Exception("Użytkownik ma przypisana ciezarowke");
                }

            }

        }


        Role role = roleDBService.getRoleByName("Uzytkownik");
        user.setRole(role);
        user.setCompany(null);
        userDBService.userUpdate(user);
    }


}
