package org.example.controller;


import org.example.dto.*;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.exceptions.UserDoesntExistsException;
import org.example.model.*;
import org.example.service.dbService.*;
import org.example.service.managementService.CompanyManagementService;
import org.example.service.managementService.UserManagementService;
import org.example.service.managementService.VehicleManagenemtSeviceImpl;
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
    private final TruckDBService truckDBService;
    private final TrailerDBService trailerDBService;
    private final TruckTrailerDBService truckTrailerDBService;
    private final UserTruckDBService userTruckDBService;
    private final VehicleManagenemtSeviceImpl vehicleManagenemtSevice;

    @Autowired
    public VehicleController(UserManagementService userManagementService,
                             UserDBService userDBService,
                             TruckDBService truckDBService,
                             TrailerDBService trailerDBService,
                             TruckTrailerDBService truckTrailerDBService,
                             UserTruckDBService userTruckDBService,
                             VehicleManagenemtSeviceImpl vehicleManagenemtSevice) {
        this.userManagementService = userManagementService;
        this.userDBService = userDBService;
        this.truckDBService = truckDBService;
        this.trailerDBService = trailerDBService;
        this.truckTrailerDBService = truckTrailerDBService;
        this.userTruckDBService = userTruckDBService;
        this.vehicleManagenemtSevice =  vehicleManagenemtSevice;
    }




    @PostMapping("/addTruck")
    public ResponseEntity<Object> companyAddTruck(@RequestBody TruckDto truckAddDto,
                                                @RequestHeader("Authorization") String authorizationHeader) {

        try {

            User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);
            Truck truck = vehicleManagenemtSevice.createTruck(user, truckAddDto);
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
            Trailer trailer = vehicleManagenemtSevice.createTrailer(user, trailerAddDto);
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

        User user = userDBService.getUser(bindDriverTruckTrailerDto.getUser_id());
        Truck truck = truckDBService.getTruck(bindDriverTruckTrailerDto.getTruck_id());
        Trailer trailer = trailerDBService.getTrailer(bindDriverTruckTrailerDto.getTrailer_id());

        vehicleManagenemtSevice.bindDriverTruckTrailer(user, truck, trailer);

        return ResponseEntity.ok("Połączono");
    }

    @PostMapping("/unbindDriverTruckTrailer")
    public ResponseEntity<Object> unbindDriverTruckTrailer(@RequestHeader("Authorization") String authorizationHeader, @RequestBody BindDriverTruckTrailerDto bindDriverTruckTrailerDto) {


        User_Truck userTruck = vehicleManagenemtSevice.getUserTruckRel(bindDriverTruckTrailerDto);
        Truck_Trailer truckTrailer = vehicleManagenemtSevice.getTruckTrailerRel(bindDriverTruckTrailerDto, userTruck);

        userTruckDBService.deleteUserTruck(userTruck.getUser_truck_id());
        truckTrailerDBService.deleteTruckTrailer(truckTrailer.getId());

        return ResponseEntity.ok("Rozłączono");
    }

    @GetMapping("/getUnbindedDrivers")
    public ResponseEntity<List<User>> getDrivers(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        List<User> res = vehicleManagenemtSevice.getUnbindedDrivers(user);

        return ResponseEntity.ok(res);
    }


    @GetMapping("/getBindedTrucks")
    public ResponseEntity<List<BindedTrucksDto>> getBindedTrucks(@RequestHeader("Authorization") String authorizationHeader) {
        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        List<BindedTrucksDto> res = vehicleManagenemtSevice.getBindedTrucks(user);


        return ResponseEntity.ok(res);
    }



}
