package org.example.service.managementService;


import org.example.dto.RouteAddDto;
import org.example.dto.RouteDataDto;
import org.example.dto.RouteDto;
import org.example.model.*;
import org.example.service.dbService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RouteManagementServiceImpl implements RouteManagementService{

    private final AddressDBService addressDBService;
    private final TruckDBService truckDBService;
    private final RouteTruckDBService routeTruckDBService;
    private final RouteDBService routeDBService;
    private final CommissionDBService commissionDBService;

    @Autowired
    public RouteManagementServiceImpl(TruckDBService truckDBService,
                                      AddressDBService addressDBService,
                                      RouteDBService routeDBService,
                                      CommissionDBService commissionDBService,
                                      RouteTruckDBService routeTruckDBService) {
        this.truckDBService = truckDBService;
        this.addressDBService = addressDBService;
        this.routeTruckDBService = routeTruckDBService;
        this.routeDBService = routeDBService;
        this.commissionDBService = commissionDBService;
    }


    public void createRoute(RouteAddDto routeAddDto, User user){
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
        route.setCompany(user.getCompany());
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
    }


    public List<RouteDto> getRoutes(User user){
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


        return res;

    }


    public RouteDataDto getRouteDate(Route route, User user){

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

        return routeDto;
    }



}
