package org.example.controller;


import org.example.dto.*;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.exceptions.UserDoesntExistsException;
import org.example.model.*;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final RouteAddressDBService routeAddressDBService;
    private final AddressDBService addressDBService;
    private final CommissionDBService commissionDBService;
    private final RouteTruckDBService routeTruckDBService;


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
                           RouteDBService routeDBService,
                           RouteAddressDBService routeAddressDBService,
                           AddressDBService addressDBService,
                           CommissionDBService commissionDBService,
                           RouteTruckDBService routeTruckDBService) {
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
        this.routeAddressDBService = routeAddressDBService;
        this.addressDBService = addressDBService;
        this.commissionDBService = commissionDBService;
        this.routeTruckDBService = routeTruckDBService;
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

            Address address = new Address();
            address.setAddress(routeAddDto.getAddress());
            address.setCity(routeAddDto.getCity());
            address.setHouse_number(routeAddDto.getHouseNumber());
            address.setGPS_X(Double.valueOf(routeAddDto.getGpsX()));
            address.setGPS_Y(Double.valueOf(routeAddDto.getGpsY()));
            address.setZip_code(routeAddDto.getZipCode());
            addressDBService.saveAddress(address);


            Truck truck = truckDBService.getTruck(Integer.parseInt(routeAddDto.getDriver()));

            Route route = new Route();
            route.setName(routeAddDto.getName());
            route.setDate_start(Date.valueOf(routeAddDto.getDate()));
            route.getAddresses().add(address);
            //route.setTruck(truck);
            route.setCompany(user.getCompany());
            //route.setUser(u);
            route.setStatus(false);
            routeDBService.saveRoute(route);

            for(User u : truck.getUsers()) {
                Route_Truck routeTruck = new Route_Truck();
                routeTruck.setTruck_id(truck);
                routeTruck.setUser_id(u);
                routeTruck.setTrailer_id(truck.getTrailers().stream().findFirst().get());
                routeTruck.setRoute_id(route);

                routeTruckDBService.saveRouteTruck(routeTruck);
            }


            /*for(User u : truck.getUsers()){

                Route route = new Route();
                route.setName(routeAddDto.getName());
                route.setDate_start(Date.valueOf(routeAddDto.getDate()));
                route.getAddresses().add(address);
                route.setTruck(truck);
                route.setCompany(user.getCompany());
                route.setUser(u);
                route.setStatus(false);
                routeDBService.saveRoute(route);
            }*/



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

        List<RouteDto> routes = routeDBService.getRouteByCompany(user.getCompany()).stream().map(
                x -> {
                    RouteDto dto = new RouteDto();
                    dto.setData(x.getDate_start().toString());
                    dto.setName(x.getName());
                    dto.setId(x.getRoute_id());
                    return dto;})
                .toList();


        Set<String> set = new HashSet<>();
        List<RouteDto> res = new ArrayList<>();

        for(RouteDto r : routes){
            if(set.add(r.getName())){
                res.add(r);
            }
        }




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

        RouteDataDto routeDto = new RouteDataDto();

        routeDto.setDate(route.getDate_start().toString());
        routeDto.setDriver(user.getName() + " " + user.getSurname());
        routeDto.setCity(route.getAddresses().stream().toList().get(0).getCity());
        routeDto.setGpsX(route.getAddresses().stream().toList().get(0).getGPS_X());

        routeDto.setGpsY(route.getAddresses().stream().toList().get(0).getGPS_Y());
        routeDto.setHouseNumber(route.getAddresses().stream().toList().get(0).getHouse_number());
        routeDto.setZipCode(route.getAddresses().stream().toList().get(0).getZip_code());
        routeDto.setAddress(route.getAddresses().stream().toList().get(0).getAddress());

        routeDto.setTruckReg(route.getTruck().getRegistration_number());
        routeDto.setTruckReg(route.getTruck().getModel());
        routeDto.setTrailerDesc(route.getTruck().getTrailers().stream().findFirst().get().getDescription());
        routeDto.setRoute_id(route.getRoute_id());
        routeDto.setName(route.getName());
        routeDto.setTruckModel(route.getTruck().getModel());

        List<Commission> commissions = commissionDBService.getCommissionByRoute(route);
        List<Commission> commissionsRes = commissions.stream().map(x->
                {
                    x.setRoute(null);
                    return x;
                }).toList();


        routeDto.setPackages(commissionsRes);




        //return ResponseEntity.ok("Zakonczono");
        return ResponseEntity.ok(routeDto);
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
