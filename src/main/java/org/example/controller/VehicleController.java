package org.example.controller;


import org.example.dto.BindDriverTruckTrailerDto;
import org.example.dto.TrailerDto;
import org.example.dto.TruckDto;
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


    @Autowired
    public VehicleController(UserManagementService userManagementService,
                             UserDBService userDBService,
                             JwtTokenUtil jwtTokenUtil,
                             CompanyDBService companyDBService,
                             CompanyManagementService companyManagementService,
                             TruckDBService truckDBService,
                             TrailerDBService trailerDBService,
                             TruckTrailerDBService truckTrailerDBService,
                             UserTruckDBService userTruckDBService) {
        this.userManagementService = userManagementService;
        this.userDBService = userDBService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.companyDBService = companyDBService;
        this.companyManagementService = companyManagementService;
        this.truckDBService = truckDBService;
        this.trailerDBService = trailerDBService;
        this.truckTrailerDBService = truckTrailerDBService;
        this.userTruckDBService = userTruckDBService;
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

        System.out.println(bindDriverTruckTrailerDto.getTruck_id());

        User_Truck userTruck = new User_Truck();
        userTruck.setTruck(truck);
        userTruck.setUser(user);
        userTruckDBService.saveUserTruck(userTruck);

        Truck_Trailer truckTrailer = new Truck_Trailer();
        truckTrailer.setTrailer(trailer);
        truckTrailer.setTruck(truck);
        truckTrailer.setUser_id(user.getUser_id());
        truckTrailerDBService.saveTruckTrailer(truckTrailer);

        return ResponseEntity.ok("Połączono");
    }

    @PostMapping("/unbindDriverTruckTrailer")
    public ResponseEntity<Object> unbindDriverTruckTrailer(@RequestHeader("Authorization") String authorizationHeader, @RequestBody BindDriverTruckTrailerDto bindDriverTruckTrailerDto) {
        //User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        //List<User> users = userDBService.getUserByCompany(user.getCompany());


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




}
