package org.example.service.managementService;

import org.example.dto.CompanyFormDto;
import org.example.dto.RegisterFormDto;
import org.example.dto.UserUpdateDto;
import org.example.model.Company;
import org.example.model.User;

public interface CompanyManagementService {

    Company fillFields(CompanyFormDto companyFormDto, String authorizationHeader);
    public Company updateFields(CompanyFormDto companyFormDto, String authorizationHeader);
}
