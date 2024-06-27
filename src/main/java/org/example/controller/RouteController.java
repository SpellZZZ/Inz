package org.example.controller;


import org.example.dto.*;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.exceptions.UserDoesntExistsException;
import org.example.model.*;
import org.example.service.dbService.*;
import org.example.service.managementService.CompanyManagementService;
import org.example.service.managementService.RouteManagementService;
import org.example.service.managementService.RouteManagementServiceImpl;
import org.example.service.managementService.UserManagementService;
import org.example.util.JwtTokenUtil;
import org.example.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController("/route")
public class RouteController {


    private final UserManagementService userManagementService;
    private final RouteDBService routeDBService;
    private final RouteManagementServiceImpl routeManagementService;


    @Autowired
    public RouteController(UserManagementService userManagementService,
                           RouteDBService routeDBService,
                           RouteManagementServiceImpl routeManagementService) {
        this.userManagementService = userManagementService;
        this.routeDBService = routeDBService;
        this.routeManagementService = routeManagementService;
    }


    @PostMapping("/startRoute")
    public ResponseEntity<Object> startRoute(@RequestBody RouteAddDto routeAddDto,
                                                     @RequestHeader("Authorization") String authorizationHeader) {


        try {

            User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
            List<Route> routes = routeDBService.getRouteByCompany(user.getCompany());

            for(Route r : routes){
                if(r.getName().equals(routeAddDto.getName())) return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, "taki przejazd istnieje");
            }

            routeManagementService.createRoute(routeAddDto, user);

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

    @GetMapping("/getRoutes")
    public ResponseEntity<List<RouteDto>> getRoutes(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        List<RouteDto> res = routeManagementService.getRoutes(user);
        return ResponseEntity.ok(res);
    }


    @GetMapping("/endRoute")
    public ResponseEntity<Object> endRoute(@RequestHeader("Authorization") String authorizationHeader, @RequestParam int id) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
        Route route = routeDBService.getRoute(id);
        route.setStatus(true);
        routeDBService.saveRoute(route);

        return ResponseEntity.ok("Zakonczono");
    }



    @GetMapping("/getRouteData")
    public ResponseEntity<RouteDataDto> getRouteDate(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
        List<Route> routes = routeDBService.getRouteByUser(user);

        Route route = routes.stream().filter(x ->
                !x.isStatus()).findFirst().get();

        RouteDataDto routeDto = routeManagementService.getRouteDate(route, user);

        return ResponseEntity.ok(routeDto);
    }




}
