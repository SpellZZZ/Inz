package org.example.controller;


import org.example.dto.AddCommissionToRouteDto;
import org.example.dto.CommissionDto;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.model.Address;
import org.example.model.Commission;
import org.example.model.Route;
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

            addressStart.setAddress(commissionDto.getAddressstart());
            addressStart.setGPS_X(commissionDto.getGps_xstart());
            addressStart.setGPS_Y(commissionDto.getGps_ystart());
            addressStart.setHouse_number(commissionDto.getHouse_numberstart());
            addressStart.setZip_code(commissionDto.getZip_codestart());
            addressStart.setCity(commissionDto.getCitystart());
            addressDBService.saveAddress(addressStart);

            addressEnd.setAddress(commissionDto.getAddressend());
            addressEnd.setGPS_X(commissionDto.getGps_xend());
            addressEnd.setGPS_Y(commissionDto.getGps_yend());
            addressEnd.setHouse_number(commissionDto.getHouse_numberend());
            addressEnd.setZip_code(commissionDto.getZip_codeend());
            addressEnd.setCity(commissionDto.getCityend());
            addressDBService.saveAddress(addressEnd);

            commission.setDelivery_pickup(addressStart);
            commission.setDelivery_endpoint(addressEnd);
            commission.setDescription(commissionDto.getDescription());
            commission.setX(commissionDto.getXpackage());
            commission.setY(commissionDto.getYpackage());
            commission.setZ(commissionDto.getZpackage());
            commission.setMass(commissionDto.getMass());
            commission.setStackable(commissionDto.getStackable());
            commission.setCount(commissionDto.getCount());

            commission.setUser(userManagementService.getUserByAuthorizationHeader(authorizationHeader));
            commission.setDate_of_placement(Date.valueOf(LocalDate.now()));
            commission.setIs_loaded(false);
            commission.setIs_unloaded(false);
            commission.setIs_selected(false);

            commissionDBService.saveCommission(commission);


            /*System.out.println(commissionDto.getGPS_XStart());
            System.out.println(commissionDto.getGPS_YStart());
            System.out.println(commissionDto.getGPS_XEnd());
            System.out.println(commissionDto.getGPS_YEnd());*/

            System.out.println(commissionDto.getXpackage());
            System.out.println(commissionDto.getYpackage());
            System.out.println(commissionDto.getZpackage());
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
        List<Commission> commissions = commissionDBService.getCommissions();
        List<Commission> res = commissions.stream().filter(x ->
                                x.getRoute() == null && x.getCanceled() == 0).toList();



        return ResponseEntity.ok(res);
    }

    @PostMapping("/addCommissionToRoute")
    public ResponseEntity<Object> addCommissionToRoute(@RequestHeader("Authorization") String authorizationHeader,
                                                       @RequestBody AddCommissionToRouteDto addCommissionToRouteDto) {


        Route route = routeDBService.getRoute(Integer.valueOf(addCommissionToRouteDto.getRoute_id()));
        int count = commissionDBService.getCommissionByRoute(route).size();

        for(int i = 0 ; i < addCommissionToRouteDto.getPackages().size(); i++){
            Commission c = commissionDBService.getCommission(addCommissionToRouteDto.getPackages().get(i));
            c.setRoute(route);
            c.setIs_selected(true);
            c.setPoint_number_start(count++);
            c.setPoint_number_end(count++);
            commissionDBService.updateCommission(c);
        }

        return ResponseHelper.createSuccessResponse("Dodano paczki do przejazdu");
    }



    @PostMapping("/commissionLoaded")
    public ResponseEntity<Object> commissionLoaded(@RequestHeader("Authorization") String authorizationHeader, @RequestParam int id) {

        Commission commission = commissionDBService.getCommission(id);
        commission.setIs_loaded(!commission.getIs_loaded());
        commissionDBService.updateCommission(commission);
        return ResponseEntity.ok("Zmieniono status");
    }




    @PostMapping("/commissionUnloaded")
    public ResponseEntity<Object> getCommissions(@RequestHeader("Authorization") String authorizationHeader, @RequestParam int id) {
        Commission commission = commissionDBService.getCommission(id);
        commission.setIs_unloaded(!commission.getIs_unloaded());
        commissionDBService.updateCommission(commission);
        return ResponseEntity.ok("Zmieniono status");

    }

    @GetMapping("/getUserCommission/{id}")
    public ResponseEntity<Commission> getUserCommission(@RequestHeader("Authorization") String authorizationHeader,@PathVariable  int id) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
        List<Commission> resArray = commissionDBService.getCommissionByUser(user);
        Commission res = resArray.stream().filter(x -> x.getCommission_id() == id).findFirst().get();

        return ResponseEntity.ok(res);
    }

    @PostMapping("/cancelCommission/{id}")
    public ResponseEntity<Object> cancelCommission(@RequestHeader("Authorization") String authorizationHeader,@PathVariable  int id) {


        Commission commission = commissionDBService.getCommission(id);
        if(commission.getRoute() != null){

            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT,"Nie udalo sie zmienic status paczki");

        }
        if (commission.getCanceled() == 0) commission.setCanceled(1);
        else commission.setCanceled(0);

        commissionDBService.saveCommission(commission);

        return ResponseHelper.createSuccessResponse("Zmieniono status paczki");
    }


}
