package org.example.controller;


import org.example.dao.CommissionDAO;
import org.example.dto.CommissionDto;
import org.example.dto.CompanyFormDto;
import org.example.dto.CompanyUsersResponseDto;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.model.Address;
import org.example.model.Commission;
import org.example.model.Company;
import org.example.model.User;
import org.example.service.dbService.*;
import org.example.service.managementService.CompanyManagementService;
import org.example.service.managementService.UserManagementService;
import org.example.util.JwtTokenUtil;
import org.example.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@RestController("/commission")
public class CommissionController {

    private final UserManagementService userManagementService;
    private final UserDBService userDBService;
    private final JwtTokenUtil jwtTokenUtil;
    private final CompanyDBService companyDBService;
    private final CompanyManagementService companyManagementService;
    private final CommissionDBService commissionDBService;
    private final RouteDBService routeDBService;
    private final AddressDBService addressDBService;

    @Autowired
    public CommissionController(UserManagementService userManagementService,
                                UserDBService userDBService,
                                JwtTokenUtil jwtTokenUtil,
                                CompanyDBService companyDBService,
                                CompanyManagementService companyManagementService,
                                CommissionDBService commissionDBService,
                                RouteDBService routeDBService,
                                AddressDBService addressDBService) {
        this.userManagementService = userManagementService;
        this.userDBService = userDBService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.companyDBService = companyDBService;
        this.companyManagementService = companyManagementService;
        this.commissionDBService = commissionDBService;
        this.routeDBService = routeDBService;
        this.addressDBService = addressDBService;

    }


    @PostMapping("/commissionCreate")
    public ResponseEntity<Object> companyCreate(@RequestBody CommissionDto commissionDto,
                                                @RequestHeader("Authorization") String authorizationHeader) {

        try {

            Commission commission = new Commission();
            Address addressStart = new Address();
            Address addressEnd = new Address();

            addressStart.setAddress(commissionDto.getAddressStart());
            addressStart.setGPS_X(commissionDto.getGPS_XStart());
            addressStart.setGPS_Y(commissionDto.getGPS_YStart());
            addressStart.setHouse_number(commissionDto.getHouse_numberStart());
            addressStart.setZip_code(commissionDto.getZip_codeStart());
            addressDBService.saveAddress(addressStart);

            addressEnd.setAddress(commissionDto.getAddressEnd());
            addressEnd.setGPS_X(commissionDto.getGPS_XEnd());
            addressEnd.setGPS_Y(commissionDto.getGPS_YEnd());
            addressEnd.setHouse_number(commissionDto.getHouse_numberEnd());
            addressEnd.setZip_code(commissionDto.getZip_codeEnd());
            addressDBService.saveAddress(addressEnd);

            commission.setDelivery_pickup(addressStart);
            commission.setDelivery_endpoint(addressEnd);
            commission.setDescription(commissionDto.getDescription());
            commission.setX(commissionDto.getXPackage());
            commission.setY(commissionDto.getYPackage());
            commission.setZ(commissionDto.getZPackage());
            commission.setMass(commissionDto.getMass());
            commission.setStackable(commissionDto.getStackable());
            commission.setCount(commissionDto.getCount());

            commission.setUser(userManagementService.getUserByAuthorizationHeader(authorizationHeader));
            commission.setDate_of_placement(Date.valueOf(LocalDate.now()));
            commission.setIs_loaded(false);
            commission.setIs_unloaded(false);

            commissionDBService.saveCommission(commission);


            /*System.out.println(commissionDto.getGPS_XStart());
            System.out.println(commissionDto.getGPS_YStart());
            System.out.println(commissionDto.getGPS_XEnd());
            System.out.println(commissionDto.getGPS_YEnd());*/

            System.out.println(commissionDto.getXPackage());
            System.out.println(commissionDto.getYPackage());
            System.out.println(commissionDto.getZPackage());
            System.out.println(commissionDto.getMass());



            return ResponseHelper.createSuccessResponse("Stworzono zlecenie");
        } catch (JwtTokenException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ObjectAlreadyExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        }
        catch (Exception ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Wystąpił błąd");
        }
    }





    @GetMapping("/getUserCommissions")
    public ResponseEntity<List<Commission>> getUserCommissions(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
        List<Commission> res = commissionDBService.getCommissionByUser(user);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/getCommissions")
    public ResponseEntity<List<Commission>> getCommissions(@RequestHeader("Authorization") String authorizationHeader) {
        List<Commission> res = commissionDBService.getCommissions();
        return ResponseEntity.ok(res);
    }



}
