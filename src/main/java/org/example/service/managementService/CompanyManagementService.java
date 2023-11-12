package org.example.service.managementService;

import org.example.dto.*;
import org.example.model.Company;
import org.example.model.User;

public interface CompanyManagementService {


    public void setOwner(Company company, String authorizationHeader);

    Company fillFields(CompanyFormDto companyFormDto, String authorizationHeader);
    public Company updateFields(CompanyFormDto companyFormDto, String authorizationHeader);

    void addNewUser(CompanyAddUserDto companyAddUserDto, String authorizationHeader) throws Exception;

    void setUserRole(CompanyUserSetRoleDto companyUserSetRoleDto, String authorizationHeader);
}
