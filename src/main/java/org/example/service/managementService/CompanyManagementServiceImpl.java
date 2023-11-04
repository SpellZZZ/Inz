package org.example.service.managementService;


import org.example.dto.CompanyAddUserDto;
import org.example.dto.CompanyFormDto;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.UserDoesntExistsException;
import org.example.model.Company;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.JwtAuthService;
import org.example.service.dbService.CompanyDBService;
import org.example.service.dbService.RoleDBService;
import org.example.service.dbService.UserDBService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyManagementServiceImpl implements CompanyManagementService {

    private final CompanyDBService companyDBService;
    private final JwtAuthService jwtAuthService;
    private final UserManagementService userManagementService;
    private final RoleDBService roleDBService;
    private final UserDBService userDBService;

    @Autowired
    public CompanyManagementServiceImpl(CompanyDBService companyDBService,
                                        JwtAuthService jwtAuthService,
                                        UserManagementService userManagementService,
                                        RoleDBService roleDBService,
                                        UserDBService userDBService
    ) {
        this.companyDBService = companyDBService;
        this.jwtAuthService = jwtAuthService;
        this.userManagementService = userManagementService;
        this.roleDBService = roleDBService;
        this.userDBService = userDBService;
    }
    @Override
    public void setOwner(Company company, String authorizationHeader) {

        final String token = jwtAuthService.authenticateToken(authorizationHeader);

        if (token == null) {
            throw new JwtTokenException("Wystąpił błąd z tokenem");
        }
        User user = userManagementService.getUserByToken(token);
        user.setCompany(company);
        Role role = roleDBService.getRoleByName("Wlasciciel");
        user.setRole(role);
        userDBService.userUpdate(user);


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

    @Override
    public void addNewUser(CompanyAddUserDto companyAddUserDto, String authorizationHeader) {

        final String token = jwtAuthService.authenticateToken(authorizationHeader);

        if (token == null) {
            throw new JwtTokenException("Wystąpił błąd z tokenem");
        }

        User owner = userManagementService.getUserByToken(token);
        Company company = owner.getCompany();
        User user = userDBService.getUserByUserName(companyAddUserDto.getUserLogin());

        if(user == null) {
            throw new UserDoesntExistsException("Nie ma takiego uzytkownika");
        }

        user.setCompany(company);
        userDBService.userUpdate(user);
    }


}
