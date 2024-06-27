package org.example.controller;

import org.example.dto.*;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.exceptions.UserDoesntExistsException;
import org.example.model.Company;
import org.example.model.User;
import org.example.service.dbService.*;
import org.example.service.managementService.CompanyManagementService;
import org.example.service.managementService.UserManagementService;
import org.example.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/company")
public class CompanyController {



    private final UserManagementService userManagementService;
    private final CompanyDBService companyDBService;
    private final CompanyManagementService companyManagementService;

    @Autowired
    public CompanyController(UserManagementService userManagementService,
                             CompanyDBService companyDBService,
                             CompanyManagementService companyManagementService
                             ) {
        this.userManagementService = userManagementService;
        this.companyDBService = companyDBService;
        this.companyManagementService = companyManagementService;
    }




    @PostMapping("/companyCreate")
    public ResponseEntity<Object> companyCreate(@RequestBody CompanyFormDto companyFormDto,
                                             @RequestHeader("Authorization") String authorizationHeader) {

        try {
            validateCompanyNotExists(companyFormDto);
            Company company = createCompanyFromDto(companyFormDto, authorizationHeader);
            companyDBService.saveCompany(company);
            companyManagementService.setOwner(company, authorizationHeader);
            return ResponseHelper.createSuccessResponse("Firma stworzona");
        } catch (JwtTokenException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ObjectAlreadyExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        }
        catch (Exception ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Wystąpił błąd");
        }
    }

    private void validateCompanyNotExists(CompanyFormDto companyFormDto) {
        if (companyDBService.getCompanyByName(companyFormDto.getCompany_name()) != null) {
            throw new ObjectAlreadyExistsException("Obiekt już istnieje.");
        }
    }

    private Company createCompanyFromDto(CompanyFormDto companyFormDto, String authorizationHeader) {
        return companyManagementService.fillFields(companyFormDto, authorizationHeader);
    }



//testing
    @PostMapping("/companyAddUser")
    public ResponseEntity<Object> companyAddUser(@RequestBody CompanyAddUserDto companyAddUserDto,
                                             @RequestHeader("Authorization") String authorizationHeader) {

        try {
            companyManagementService.addNewUser(companyAddUserDto, authorizationHeader);
            return ResponseHelper.createSuccessResponse("Uzytkownik dodany");
        } catch (JwtTokenException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ObjectAlreadyExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        } catch (UserDoesntExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        catch (Exception ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Wystąpił błąd");
        }
    }

    @PostMapping("/companyDeleteUser")
    public ResponseEntity<Object> companyDeleteUser(@RequestBody CompanyAddUserDto companyAddUserDto,
                                                 @RequestHeader("Authorization") String authorizationHeader) {

        try {
            companyManagementService.delete(companyAddUserDto, authorizationHeader);
            return ResponseHelper.createSuccessResponse("Uzytkownik usuniety");
        } catch (JwtTokenException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ObjectAlreadyExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        } catch (UserDoesntExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        catch (Exception ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Wystąpił błąd");
        }
    }


    @PostMapping("/companyUserSetRole")
    public ResponseEntity<Object> companyUserSetRole(@RequestBody CompanyUserSetRoleDto companyUserSetRoleDto,
                                                 @RequestHeader("Authorization") String authorizationHeader) {

        try {
            companyManagementService.setUserRole(companyUserSetRoleDto, authorizationHeader);
            return ResponseHelper.createSuccessResponse("Rola zmieniona");
        } catch (JwtTokenException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ObjectAlreadyExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        } catch (UserDoesntExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        catch (Exception ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Wystąpił błąd");
        }
    }


    @GetMapping("/companyGetUsers")
    public ResponseEntity<List<CompanyUsersResponseDto>> companyGetUser(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
        List<CompanyUsersResponseDto> res = companyManagementService.getCompanyUsers(user);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/companyGetBindedDrivers")
    public ResponseEntity<List<BindedDriversDto>> companyGetBindedDrivers(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        List<BindedDriversDto> res = companyManagementService.companyGetBindedDrivers(user);

        return ResponseEntity.ok(res);
    }


}
