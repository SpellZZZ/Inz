package org.example.service.managementService;

import org.example.dto.*;
import org.example.model.Company;
import org.example.model.User;

import java.util.List;

public interface CompanyManagementService {


    public void setOwner(Company company, String authorizationHeader);

    Company fillFields(CompanyFormDto companyFormDto, String authorizationHeader);
    void addNewUser(CompanyAddUserDto companyAddUserDto, String authorizationHeader) throws Exception;

    void setUserRole(CompanyUserSetRoleDto companyUserSetRoleDto, String authorizationHeader);

    void delete(CompanyAddUserDto companyAddUserDto, String authorizationHeader);

    List<CompanyUsersResponseDto> getCompanyUsers(User user);

    List<BindedDriversDto> companyGetBindedDrivers(User user);
}
