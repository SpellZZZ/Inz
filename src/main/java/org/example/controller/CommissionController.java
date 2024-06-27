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
import org.example.service.managementService.CommissionManagementServiceImpl;
import org.example.service.managementService.UserManagementService;
import org.example.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/commission")
public class CommissionController {

    private final UserManagementService userManagementService;
    private final CommissionDBService commissionDBService;
    private final RouteDBService routeDBService;
    private final CommissionManagementServiceImpl commissionManagementService;

    @Autowired
    public CommissionController(UserManagementService userManagementService,
                                CommissionDBService commissionDBService,
                                RouteDBService routeDBService,
                                CommissionManagementServiceImpl commissionManagementService) {
        this.userManagementService = userManagementService;
        this.commissionDBService = commissionDBService;
        this.routeDBService = routeDBService;
        this.commissionManagementService = commissionManagementService;

    }


    @PostMapping("/commissionCreate")
    public ResponseEntity<Object> commissionCreate(@RequestBody CommissionDto commissionDto,
                                                @RequestHeader("Authorization") String authorizationHeader) {

        try {

            Address addressStart = commissionManagementService.addressStartCreate(commissionDto);
            Address addressEnd = commissionManagementService.addressEndCreate(commissionDto);
            commissionManagementService.commissionCreate(commissionDto,addressStart,addressEnd,authorizationHeader);


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
                                x.getRoute() == null).toList();



        return ResponseEntity.ok(res);
    }

    @PostMapping("/addCommissionToRoute")
    public ResponseEntity<Object> addCommissionToRoute(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody AddCommissionToRouteDto addCommissionToRouteDto) {


        Route route = routeDBService.getRoute(Integer.parseInt(addCommissionToRouteDto.getRoute_id()));

        for(int i = 0 ; i < addCommissionToRouteDto.getPackages().size(); i++){
            Commission c = commissionDBService.getCommission(addCommissionToRouteDto.getPackages().get(i));
            c.setRoute(route);
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


}
