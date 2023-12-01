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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController("/vehicle")
public class VehicleController {



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
    private final RouteTruckDBService routeTruckDBService;

    @Autowired
    public VehicleController(UserManagementService userManagementService,
                             UserDBService userDBService,
                             JwtTokenUtil jwtTokenUtil,
                             CompanyDBService companyDBService,
                             CompanyManagementService companyManagementService,
                             TruckDBService truckDBService,
                             TrailerDBService trailerDBService,
                             TruckTrailerDBService truckTrailerDBService,
                             UserTruckDBService userTruckDBService,
                             RouteDBService routeDBService,
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
        this.routeTruckDBService = routeTruckDBService;
    }




    @PostMapping("/addTruck")
    public ResponseEntity<Object> companyAddTruck(@RequestBody TruckDto truckAddDto,
                                                @RequestHeader("Authorization") String authorizationHeader) {

        try {


            User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
            Truck truck = new Truck();

            truck.setCompany(user.getCompany());
            truck.setModel(truckAddDto.getModel());
            truck.setTruck_mass(truckAddDto.getMass());
            truck.setRegistration_number(truckAddDto.getLicensePlate());
            truckDBService.saveTruck(truck);


            return ResponseHelper.createSuccessResponse("Pojazd dodany");
        } catch (JwtTokenException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ObjectAlreadyExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        }
        catch (Exception ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Wystąpił błąd");
        }
    }



    @PostMapping("/addTrailer")
    public ResponseEntity<Object> companyAddTrailer(@RequestBody TrailerDto trailerAddDto,
                                                 @RequestHeader("Authorization") String authorizationHeader) {

        try {

            User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
            Trailer trailer = new Trailer();

            trailer.setCompany(user.getCompany());
            trailer.set_detachable(trailerAddDto.isDismount());
            trailer.setTrailer_mass(trailerAddDto.getMass());
            trailer.setMax_payload(trailerAddDto.getMaxMass());
            trailer.setX(trailerAddDto.getWidth());
            trailer.setY(trailerAddDto.getHeight());
            trailer.setZ(trailerAddDto.getVolume());

            trailerDBService.saveTrailer(trailer);


            return ResponseHelper.createSuccessResponse("Naczepa dodana");
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


    @PostMapping("/deleteTruck")
    public ResponseEntity<Object> deleteTruck(@RequestBody TruckDto truckAddDto,
                                                @RequestHeader("Authorization") String authorizationHeader) {

        try {


            User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
            Truck truck = new Truck();

            truck.setCompany(user.getCompany());
            truck.setModel(truckAddDto.getModel());
            truck.setTruck_mass(truckAddDto.getMass());
            truck.setRegistration_number(truckAddDto.getLicensePlate());
            truckDBService.saveTruck(truck);


            return ResponseHelper.createSuccessResponse("Pojazd dodany");
        } catch (JwtTokenException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ObjectAlreadyExistsException ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        }
        catch (Exception ex) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Wystąpił błąd");
        }
    }



    @PostMapping("/deleteTrailer")
    public ResponseEntity<Object> deleteTrailer(@RequestBody TrailerDto trailerAddDto,
                                                 @RequestHeader("Authorization") String authorizationHeader) {

        try {

            User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
            Trailer trailer = new Trailer();

            trailer.setCompany(user.getCompany());
            trailer.set_detachable(trailerAddDto.isDismount());
            trailer.setTrailer_mass(trailerAddDto.getMass());
            trailer.setMax_payload(trailerAddDto.getMaxMass());
            trailer.setX(trailerAddDto.getWidth());
            trailer.setY(trailerAddDto.getHeight());
            trailer.setZ(trailerAddDto.getVolume());

            trailerDBService.saveTrailer(trailer);


            return ResponseHelper.createSuccessResponse("Naczepa dodana");
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

    @GetMapping("/getTrucks")
    public ResponseEntity<List<Truck>> getTrucks(@RequestHeader("Authorization") String authorizationHeader) {

            User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
            List<Truck> res = truckDBService.getTruckByCompany(user.getCompany());
            return ResponseEntity.ok(res);

    }

    @GetMapping("/getTrailers")
    public ResponseEntity<List<Trailer>> getTrailers(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        List<Trailer> res = trailerDBService.getTrailerByCompany(user.getCompany());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/bindDriverTruckTrailer")
    public ResponseEntity<Object> bindDriverTruckTrailer(@RequestHeader("Authorization") String authorizationHeader, @RequestBody BindDriverTruckTrailerDto bindDriverTruckTrailerDto) {
        //User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        //List<User> users = userDBService.getUserByCompany(user.getCompany());


        User user = userDBService.getUser(bindDriverTruckTrailerDto.getUser_id());
        Truck truck = truckDBService.getTruck(bindDriverTruckTrailerDto.getTruck_id());
        Trailer trailer = trailerDBService.getTrailer(bindDriverTruckTrailerDto.getTrailer_id());


        Truck_Trailer truckTrailer = new Truck_Trailer();
        truckTrailer.setTrailer(trailer);
        truckTrailer.setTruck(truck);
        truckTrailer.setUser_id(user.getUser_id());
        truckTrailerDBService.saveTruckTrailer(truckTrailer);


        User_Truck userTruck = new User_Truck();
        userTruck.setTruck(truck);
        userTruck.setUser(user);
        userTruckDBService.saveUserTruck(userTruck);

        return ResponseEntity.ok("Połączono");
    }

    @PostMapping("/unbindDriverTruckTrailer")
    public ResponseEntity<Object> unbindDriverTruckTrailer(@RequestHeader("Authorization") String authorizationHeader, @RequestBody BindDriverTruckTrailerDto bindDriverTruckTrailerDto) {
        //User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        //List<User> users = userDBService.getUserByCompany(user.getCompany());

        List<Route_Truck> routeTrucks = routeTruckDBService.getRouteTruckByUser(userDBService.getUser(bindDriverTruckTrailerDto.getUser_id()));


        for(Route_Truck x : routeTrucks) {
            if(!x.getRoute_id().isStatus())
                return ResponseHelper.createErrorResponse(HttpStatus.CONFLICT, "Ten kierowca ma niezakończoną trasę");
         }



        User_Truck userTruck = userTruckDBService.getUserTrucks().stream()
                .filter(x->
                        x.getUser().getUser_id()==bindDriverTruckTrailerDto.getUser_id()
                && x.getTruck().getTruck_id()== bindDriverTruckTrailerDto.getTruck_id()).findFirst().get();

        Truck_Trailer truckTrailer = truckTrailerDBService.getTruckTrailers().stream()
                        .filter(x->
                                x.getTruck().getTruck_id()== bindDriverTruckTrailerDto.getTruck_id()
                        && x.getTrailer().getTrailer_id() == bindDriverTruckTrailerDto.getTrailer_id()
                        && x.getUser_id() == userTruck.getUser().getUser_id()).findFirst().get();


        userTruckDBService.deleteUserTruck(userTruck.getUser_truck_id());
        truckTrailerDBService.deleteTruckTrailer(truckTrailer.getId());




        return ResponseEntity.ok("Rozłączono");
    }

    @GetMapping("/getUnbindedDrivers")
    public ResponseEntity<List<User>> getDrivers(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        List<User> res = userDBService.getUserByCompany(user.getCompany()).stream().filter(
                        x -> x.getRole().getRole_name().equals("Kierowca")
                            && x.getTrucks().isEmpty())
                .collect(Collectors.toList());;

        return ResponseEntity.ok(res);
    }


    @GetMapping("/getBindedTrucks")
    public ResponseEntity<List<BindedTrucksDto>> getBindedTrucks(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        List<User> users = userDBService.getUserByCompany(user.getCompany()).stream().filter(
                        x -> x.getRole().getRole_name().equals("Kierowca")
                                && x.getTrucks().size() > 0)
                .collect(Collectors.toList());



        List<BindedTrucksDto> trucks = users
                .stream().map(x -> {BindedTrucksDto dto = new BindedTrucksDto();
                    dto.setTruck_id(userTruckDBService.getUserTrucks().stream()
                            .filter(y->
                                    y.getUser().getUser_id()==x.getUser_id()).findFirst().get().getTruck().getTruck_id());
                    dto.setTrailer_id(truckTrailerDBService.getTruckTrailers().stream()
                            .filter(y ->
                                    y.getUser_id() == x.getUser_id()).findFirst().get().getTrailer().getTrailer_id());

                    dto.setTruckModel(truckDBService.getTruck(dto.getTruck_id()).getModel());
                    dto.setTruckReg(truckDBService.getTruck(dto.getTruck_id()).getRegistration_number());

                    dto.setTrailerDesc(trailerDBService.getTrailer(dto.getTrailer_id()).getDescription());
                    dto.setToString(
                            dto.getTruckModel() + " " +
                            dto.getTruckReg() + " " +
                            dto.getTrailerDesc());

                    return dto;})
                .toList();

        List<BindedTrucksDto> res = new ArrayList<>();

        Set<String> unique = new HashSet<>();

        for(BindedTrucksDto s : trucks){
            if(unique.add(s.getToString())){
                res.add(s);
            }
        }


        return ResponseEntity.ok(res);
    }



}
