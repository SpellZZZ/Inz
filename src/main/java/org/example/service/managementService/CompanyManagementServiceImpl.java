package org.example.service.managementService;


import org.example.dto.CompanyFormDto;
import org.example.exceptions.JwtTokenException;
import org.example.model.Company;
import org.example.service.JwtAuthService;
import org.example.service.dbService.CompanyDBService;
import org.example.service.dbService.UserDBService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyManagementServiceImpl implements CompanyManagementService {

    private final CompanyDBService companyDBService;
    private final JwtAuthService jwtAuthService;

    @Autowired
    public CompanyManagementServiceImpl(CompanyDBService companyDBService,
                                        JwtAuthService jwtAuthService
    ) {
        this.companyDBService = companyDBService;
        this.jwtAuthService = jwtAuthService;
    }



    //todo pobrac nick, zapisac go w userze ktoryto tworzy
    @Override
    public Company fillFields(CompanyFormDto companyFormDto, String authorizationHeader) {

        final String token = jwtAuthService.authenticateToken(authorizationHeader);

        if (token == null) {
            throw new JwtTokenException("Wystąpił błąd z tokenem");
        }

        Company company = new Company();
        company.setCompany_name(companyFormDto.getCompany_name());
        company.setCompany_nip(companyFormDto.getCompany_nip());
        return company;
    }

    @Override
    public Company updateFields(CompanyFormDto companyFormDto, String authorizationHeader) {

        final String token = jwtAuthService.authenticateToken(authorizationHeader);

        if (token == null) {
            throw new JwtTokenException("Wystąpił błąd z tokenem");
        }




        return null;
    }




}
