package org.example.service.managementService;


import org.example.dto.*;
import org.example.exceptions.UserDoesntExistsException;
import org.example.model.Company;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.JwtAuthService;
import org.example.service.dbService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyManagementServiceImpl implements CompanyManagementService {
    private final UserManagementService userManagementService;
    private final RoleDBService roleDBService;
    private final UserDBService userDBService;
    private final TruckTrailerDBService truckTrailerDBService;
    private final UserTruckDBService userTruckDBService;
    private final TruckDBService truckDBService;
    private final TrailerDBService trailerDBService;

    @Autowired
    public CompanyManagementServiceImpl(
                                        UserManagementService userManagementService,
                                        RoleDBService roleDBService,
                                        UserDBService userDBService,
                                        UserTruckDBService userTruckDBService,
                                        TruckDBService truckDBService,
                                        TrailerDBService trailerDBService,
                                        TruckTrailerDBService truckTrailerDBService
    ) {
        this.userManagementService = userManagementService;
        this.roleDBService = roleDBService;
        this.userDBService = userDBService;
        this.truckTrailerDBService = truckTrailerDBService;
        this.truckDBService = truckDBService;
        this.trailerDBService = trailerDBService;
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
    public void setUserRole(CompanyUserSetRoleDto companyUserSetRoleDto, String authorizationHeader) {
        User user = userDBService.getUserByUserName(companyUserSetRoleDto.getLogin());
        Role role = roleDBService.getRoleByName(companyUserSetRoleDto.getRole());
        user.setRole(role);
        userDBService.userUpdate(user);

    }

    @Override
    public void delete(CompanyAddUserDto companyAddUserDto, String authorizationHeader) {
        User user = userDBService.getUserByUserName(companyAddUserDto.getLogin());
        Role role = roleDBService.getRoleByName("Uzytkownik");
        user.setRole(role);
        user.setCompany(null);
        userDBService.userUpdate(user);
    }

    @Override
    public List<CompanyUsersResponseDto> getCompanyUsers(User user) {

        return userDBService.getUserByCompany(user.getCompany())
                .stream().map(x -> {CompanyUsersResponseDto dto = new CompanyUsersResponseDto();
                    dto.setLogin(x.getUsername());
                    dto.setName(x.getName());
                    dto.setSurname(x.getSurname());
                    dto.setRole(x.getRole().getRole_name());
                    return dto;})
                .toList();
    }

    @Override
    public List<BindedDriversDto> companyGetBindedDrivers(User user) {

        List<User> users = userDBService.getUserByCompany(user.getCompany()).stream().filter(
                        x -> x.getRole().getRole_name().equals("Kierowca")
                                && x.getTrucks().size() > 0 )
                .collect(Collectors.toList());




        return users
                .stream().map(x -> {BindedDriversDto dto = new BindedDriversDto();
                    dto.setUser_id(x.getUser_id());
                    dto.setTruck_id(userTruckDBService.getUserTrucks().stream()
                            .filter(y->
                                    y.getUser().getUser_id()==x.getUser_id()).findFirst().get().getTruck().getTruck_id());
                    dto.setTrailer_id(truckTrailerDBService.getTruckTrailers().stream()
                            .filter(y ->
                                    y.getUser_id() == x.getUser_id()).findFirst().get().getTrailer().getTrailer_id());

                    dto.setTruckModel(truckDBService.getTruck(dto.getTruck_id()).getModel());
                    dto.setTruckReg(truckDBService.getTruck(dto.getTruck_id()).getRegistration_number());
                    dto.setUserName(x.getName());
                    dto.setUserSurname(x.getSurname());

                    dto.setTrailerDesc(trailerDBService.getTrailer(dto.getTrailer_id()).getDescription());
                    dto.setToString(dto.getUserName() + " " +
                            dto.getUserSurname() + " " +
                            dto.getTruckModel() + " " +
                            dto.getTruckReg() + " " +
                            dto.getTrailerDesc());


                    return dto;})
                .toList();


    }


}
