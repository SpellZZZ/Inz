package org.example.controller;


import org.example.dto.*;
import org.example.exceptions.DateErrorException;
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
import java.time.LocalDate;
import java.util.*;
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

            if(!validateDate(routeAddDto.getDate(), routeAddDto.getDatePredict())) throw new DateErrorException("Wystąpił problem z datą");

            Truck truck = truckDBService.getTruck(Integer.parseInt(routeAddDto.getDriver()));
            if(!validateRoutesDates(routeTruckDBService.getRouteTruckByTruck(truck), routeAddDto.getDate(), routeAddDto.getDatePredict())) throw new DateErrorException("Daty się nakładają");

            routeTruckDBService.getRouteTruckByTruck(truck);

            Address address = new Address();
            address.setAddress(routeAddDto.getAddress());
            address.setCity(routeAddDto.getCity());
            address.setHouse_number(routeAddDto.getHouseNumber());
            address.setGPS_X(Double.valueOf(routeAddDto.getGpsX()));
            address.setGPS_Y(Double.valueOf(routeAddDto.getGpsY()));
            address.setZip_code(routeAddDto.getZipCode());
            addressDBService.saveAddress(address);


            Address addressEnd = new Address();
            addressEnd.setAddress(routeAddDto.getAddressEnd());
            addressEnd.setCity(routeAddDto.getCityEnd());
            addressEnd.setHouse_number(routeAddDto.getHouseNumberEnd());
            addressEnd.setGPS_X(Double.valueOf(routeAddDto.getGpsXEnd()));
            addressEnd.setGPS_Y(Double.valueOf(routeAddDto.getGpsYEnd()));
            addressEnd.setZip_code(routeAddDto.getZipCodeEnd());
            addressDBService.saveAddress(addressEnd);


            Route route = new Route();
            route.setName(routeAddDto.getName());
            route.setDate_start(Date.valueOf(routeAddDto.getDate()));
            route.setDate_end_predict(Date.valueOf(routeAddDto.getDatePredict()));
            //route.getAddresses().add(address);
            //route.getAddresses();
            route.setTruck(truck);
            route.setCompany(user.getCompany());
            route.setUser(/*userDBService.getUser(Integer.valueOf(routeAddDto.getDriver()))*/null);
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


            Route_Address routeAddressStart = new Route_Address();
            routeAddressStart.setAddress(address);
            routeAddressStart.setRoute(route);
            routeAddressStart.setPoint_number(0);
            routeAddressDBService.saveRouteAddress(routeAddressStart);

            Route_Address routeAddressEnd = new Route_Address();
            routeAddressEnd.setAddress(addressEnd);
            routeAddressEnd.setRoute(route);
            routeAddressEnd.setPoint_number(1);
            routeAddressDBService.saveRouteAddress(routeAddressEnd);


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
        } catch (DateErrorException ex){
            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        }
        catch (UserDoesntExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        catch (Exception ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Wystąpił błąd");
        }
    }




    @GetMapping("/getRoutes")
    public ResponseEntity<List<RouteDto>> getRoutes(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        List<Commission> startedCommission = commissionDBService.getCommissions()
                .stream().filter(x -> x.getIs_unloaded() || x.getIs_loaded())
                .toList();



        List<RouteDto> routes = routeDBService.getRouteByCompany(user.getCompany()).stream().map(
                x -> {
                    RouteDto dto = new RouteDto();
                    dto.setData(x.getDate_start().toString());
                    dto.setDataEnd(x.getDate_end_predict().toString());
                    dto.setStatus(x.isStatus());
                    dto.setName(x.getName());
                    dto.setId(x.getRoute_id());
                    dto.setStarted(false);

                    startedCommission.forEach(y ->{
                        if(y.getRoute().getRoute_id() == x.getRoute_id())
                                dto.setStarted(true);
                            });

                    return dto;})
                .toList();


        Set<String> set = new HashSet<>();
        List<RouteDto> res = new ArrayList<>();

        for(RouteDto r : routes){
            if(set.add(r.getName())){
                res.add(r);
            }
        }


        Collections.sort(res, new Comparator<RouteDto>() {
            public int compare(RouteDto o1, RouteDto o2) {
                return o1.getData().compareTo(o2.getData());
            }
        });



        return ResponseEntity.ok(res);
    }


    @PostMapping("/deleteRoute")
    public ResponseEntity<Object> deleteRoute(@RequestBody DeleteRouteDto deleteRouteDto,
                                             @RequestHeader("Authorization") String authorizationHeader) {


        Route route = routeDBService.getRoute(deleteRouteDto.getRoute_id());

        if (commissionDBService.getCommissionByRoute(route).size() >0){
            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, "Usun paczki przed usunieciem trasy");
        }


        List<Route_Address> routeAddress = routeAddressDBService.getRouteAddresses();

        List<Route_Address> addressesToDelete =
                routeAddress.stream().filter(x-> x.getRoute().getRoute_id() == route.getRoute_id()).toList();



        List<Route_Truck> routeTrucksToDelete = routeTruckDBService.getRouteTruckByRoute(route);

        addressesToDelete.forEach( x->
                routeAddressDBService.deleteRouteAddress(x.getId())
        );

        routeTrucksToDelete.forEach( x->
                routeTruckDBService.deleteRouteTruck(x.getId()));

        routeDBService.deleteRoute(route.getRoute_id());



        return ResponseEntity.ok("Udało sie usunąć trase");
    }




    @GetMapping("/getRoute/{id}")
    public ResponseEntity<RouteDetailsDto> getRoute(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        Route route = routeDBService.getRouteByCompany(user.getCompany()).stream().filter(
               x -> x.getRoute_id() == id
        ).findFirst().get();

        RouteDetailsDto routeDetailsDto = new RouteDetailsDto();
        routeDetailsDto.setRoute(route);

        List<Commission> commissionsStart = commissionDBService.getCommissionByRoute(route);
        Collections.sort(commissionsStart, new Comparator<Commission>() {
            public int compare(Commission o1, Commission o2) {
                return Integer.compare(o1.getPoint_number_start(), o2.getPoint_number_start());}
        });

        List<Commission> commissionsEnd = commissionDBService.getCommissionByRoute(route);
        Collections.sort(commissionsEnd, new Comparator<Commission>() {
            public int compare(Commission o1, Commission o2) {
                return Integer.compare(o1.getPoint_number_end(), o2.getPoint_number_end());}
        });

        routeDetailsDto.setCommissions(commissionsStart);
        routeDetailsDto.setCommissions2(commissionsEnd);



        return ResponseEntity.ok(routeDetailsDto);
    }


    @GetMapping("/endRoute")
    public ResponseEntity<Object> endRoute(@RequestHeader("Authorization") String authorizationHeader, @RequestParam int id) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
        Route route = routeDBService.getRoute(id);
        route.setDate_end(Date.valueOf(LocalDate.now()));
        route.setStatus(true);
        routeDBService.saveRoute(route);

        return ResponseEntity.ok("Zakonczono");
    }



    @GetMapping("/getRouteData")
    public ResponseEntity<RouteDataDto> getRouteData(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        //List<Route> routes = routeDBService.getRouteByUser(user);
        List<Route_Truck> routeTrucks = routeTruckDBService.getRouteTruckByUser(user);

        List<Route> routes = new ArrayList<>();
        routeTrucks.forEach(x->routes.add(x.getRoute_id()));

        Collections.sort(routes, new Comparator<Route>() {
            public int compare(Route o1, Route o2) {
                return o1.getDate_start().compareTo(o2.getDate_start());
            }
        });

        Route route;
        Optional<Route> routeOptional = routes.stream().filter(x ->
                !x.isStatus()).findFirst();


        if (routeOptional.isPresent()) {
            route = routeOptional.get();

        } else {
            return ResponseEntity.ok(null);
        }

        RouteDataDto routeDto = new RouteDataDto();

        routeDto.setDate(route.getDate_start().toString());
        routeDto.setDriver(user.getName() + " " + user.getSurname());


        routeDto.setCity(route.getAddresses().stream().toList().get(0).getCity());
        routeDto.setGpsX(route.getAddresses().stream().toList().get(0).getGPS_X());

        routeDto.setGpsY(route.getAddresses().stream().toList().get(0).getGPS_Y());
        routeDto.setHouseNumber(route.getAddresses().stream().toList().get(0).getHouse_number());
        routeDto.setZipCode(route.getAddresses().stream().toList().get(0).getZip_code());
        routeDto.setAddress(route.getAddresses().stream().toList().get(0).getAddress());

        routeDto.setCityEnd(route.getAddresses().stream().toList().get(1).getCity());
        routeDto.setGpsXEnd(route.getAddresses().stream().toList().get(1).getGPS_X());

        routeDto.setGpsYEnd(route.getAddresses().stream().toList().get(1).getGPS_Y());
        routeDto.setHouseNumberEnd(route.getAddresses().stream().toList().get(1).getHouse_number());
        routeDto.setZipCodeEnd(route.getAddresses().stream().toList().get(1).getZip_code());
        routeDto.setAddressEnd(route.getAddresses().stream().toList().get(1).getAddress());

        routeDto.setTruckReg(route.getTruck().getRegistration_number());
        routeDto.setTruckModel(route.getTruck().getModel());
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

    @GetMapping("/routeHistory")
    public ResponseEntity<List<RouteHistoryDto>> endRoute(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
        List<Route> route = routeDBService.getRouteByUser(user);
        List<Route_Truck> routeTrucks = routeTruckDBService.getRouteTruckByUser(user);

        List<Route_Truck> resF = routeTrucks.stream().filter(x->
                x.getRoute_id().isStatus()).toList();

        List<RouteHistoryDto> res = resF.stream().map(x->{
            RouteHistoryDto dto = new RouteHistoryDto();
            dto.setId(x.getId());
            dto.setTruck(x.getTruck_id().getModel());
            dto.setData_start(x.getRoute_id().getDate_start().toString());
            dto.setData_end(x.getRoute_id().getDate_end().toString());
            return dto;
        }).toList();



        return ResponseEntity.ok(res);
    }


    @PostMapping("/removeCommission")
    public ResponseEntity<Object> cancelCommission(@RequestHeader("Authorization") String authorizationHeader,@RequestBody RemoveCommissionDto removeCommissionDto) {

        Route route = routeDBService.getRoute(removeCommissionDto.getRoute_id());
        Commission commission = commissionDBService.getCommission(removeCommissionDto.getCommission_id());

        int s = commission.getPoint_number_start();
        int e = commission.getPoint_number_end();


        List<Commission> commissionList = commissionDBService.getCommissionByRoute(route);

        for (Commission c: commissionList) {
            if(s < c.getPoint_number_start())c.setPoint_number_start( c.getPoint_number_start() -1);
            if(e < c.getPoint_number_end())c.setPoint_number_end( c.getPoint_number_end() -1);
            commissionDBService.updateCommission(c);
        }


        commission.setIs_selected(null);
        commission.setPoint_number_start(0);
        commission.setPoint_number_end(0);
        commission.setRoute(null);
        commissionDBService.updateCommission(commission);

        return ResponseHelper.createSuccessResponse("Usunieto paczke z przejazdu");
    }


    private boolean validateDate(String dStart, String dEnd){

        Date start = Date.valueOf(dStart);
        Date end = Date.valueOf(dEnd);


        if(start.before(java.sql.Date.valueOf(LocalDate.now()))){
            return false;
        }

        if(start.after(end)) return false;

        return true;
    }

    private boolean validateRoutesDates(List<Route_Truck> routeTruckByTruck, String dStart, String dEnd) {

        Date start = Date.valueOf(dStart);
        Date end = Date.valueOf(dEnd);

        for(Route_Truck r : routeTruckByTruck){


            if(start.before(r.getRoute_id().getDate_start())){
                if(!end.before(r.getRoute_id().getDate_start()))
                {
                    return false;
                }
            }
            if(start.before(r.getRoute_id().getDate_start())){
                if(end.after(r.getRoute_id().getDate_start()))
                {
                    return false;
                }
            }

            if(start.after(r.getRoute_id().getDate_end_predict())){
                if(!end.after(r.getRoute_id().getDate_end_predict()))
                {
                    return false;
                }
            }

            if(start.before(r.getRoute_id().getDate_end_predict())){
                if(end.after(r.getRoute_id().getDate_end_predict()))
                {
                    return false;
                }
            }

            if(start.after(r.getRoute_id().getDate_start())){
                if(end.before(r.getRoute_id().getDate_end_predict()))
                {
                    return false;
                }
            }

        }

        return true;
    }



}
