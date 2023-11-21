package org.example.controller;


import org.example.dto.CompanyUserSetRoleDto;
import org.example.dto.RouteAddDto;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.exceptions.UserDoesntExistsException;
import org.example.model.Route;
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

@RestController("/route")
public class RouteController {


    private final UserManagementService userManagementService;
    private final UserDBService userDBService;
    private final JwtTokenUtil jwtTokenUtil;
    private final CompanyDBService companyDBService;
    private final CompanyManagementService companyManagementService;
    private final TruckDBService truckDBService;
    private final TrailerDBService trailerDBService;
    private final TruckTrailerDBService truckTrailerDBService;
    private final UserTruckDBService userTruckDBService;
    private final RouteDBService routeDBService;


    @Autowired
    public RouteController(UserManagementService userManagementService,
                             UserDBService userDBService,
                             JwtTokenUtil jwtTokenUtil,
                             CompanyDBService companyDBService,
                             CompanyManagementService companyManagementService,
                             TruckDBService truckDBService,
                             TrailerDBService trailerDBService,
                             TruckTrailerDBService truckTrailerDBService,
                             UserTruckDBService userTruckDBService,
                           RouteDBService routeDBService) {
        this.userManagementService = userManagementService;
        this.userDBService = userDBService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.companyDBService = companyDBService;
        this.companyManagementService = companyManagementService;
        this.truckDBService = truckDBService;
        this.trailerDBService = trailerDBService;
        this.truckTrailerDBService = truckTrailerDBService;
        this.userTruckDBService = userTruckDBService;
        this.routeDBService = routeDBService;
    }


    @PostMapping("/startRoute")
    public ResponseEntity<Object> startRoute(@RequestBody RouteAddDto routeAddDto,
                                                     @RequestHeader("Authorization") String authorizationHeader) {

        try {


            Route route = new Route();
            route.setUser(userDBService.getUser(routeAddDto.getUser_id()));
            route.setTruck(truckDBService.getTruck(routeAddDto.getTruck_id()));
            route.setDate_start(Date.valueOf(routeAddDto.getDate_start()));
            route.setDate_end(Date.valueOf(routeAddDto.getDate_end()));
            routeDBService.saveRoute(route);

            return ResponseHelper.createSuccessResponse("Trasa zapisana");


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

    /*@PostMapping("/endRoute")
    public ResponseEntity<Object> endRoute(@RequestParam int id,
                                                     @RequestHeader("Authorization") String authorizationHeader) {

        try {
            Route route = new Route();
            route.setUser(userDBService.getUser(routeAddDto.getUser_id()));
            route.setTruck(truckDBService.getTruck(routeAddDto.getTruck_id()));
            route.setDate_start(Date.valueOf(routeAddDto.getDate_start()));
            route.setDate_end(Date.valueOf(routeAddDto.getDate_end()));
            routeDBService.getRoute(id).set;


            return ResponseHelper.createSuccessResponse("Trasa zapisana");


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
    }*/



}
