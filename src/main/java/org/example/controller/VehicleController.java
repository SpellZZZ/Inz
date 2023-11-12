package org.example.controller;


import org.example.dto.CompanyUsersResponseDto;
import org.example.dto.TrailerDto;
import org.example.dto.TruckDto;
import org.example.exceptions.JwtTokenException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.exceptions.UserDoesntExistsException;
import org.example.model.Trailer;
import org.example.model.Truck;
import org.example.model.User;
import org.example.service.dbService.CompanyDBService;
import org.example.service.dbService.TrailerDBService;
import org.example.service.dbService.TruckDBService;
import org.example.service.dbService.UserDBService;
import org.example.service.managementService.CompanyManagementService;
import org.example.service.managementService.UserManagementService;
import org.example.util.JwtTokenUtil;
import org.example.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/vehicle")
public class VehicleController {



    private final UserManagementService userManagementService;
    private final UserDBService userDBService;
    private final JwtTokenUtil jwtTokenUtil;
    private final CompanyDBService companyDBService;
    private final CompanyManagementService companyManagementService;
    private final TruckDBService truckDBService;
    private final TrailerDBService trailerDBService;

    @Autowired
    public VehicleController(UserManagementService userManagementService,
                             UserDBService userDBService,
                             JwtTokenUtil jwtTokenUtil,
                             CompanyDBService companyDBService,
                             CompanyManagementService companyManagementService,
                             TruckDBService truckDBService,
                             TrailerDBService trailerDBService) {
        this.userManagementService = userManagementService;
        this.userDBService = userDBService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.companyDBService = companyDBService;
        this.companyManagementService = companyManagementService;
        this.truckDBService = truckDBService;
        this.trailerDBService = trailerDBService;
    }




    @PostMapping("/addTruck")
    public ResponseEntity<Object> companyCreate(@RequestBody TruckDto truckAddDto,
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
    public ResponseEntity<Object> companyAddUser(@RequestBody TrailerDto trailerAddDto,
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
    public ResponseEntity<List<TruckDto>> getTrucks(@RequestHeader("Authorization") String authorizationHeader) {


            User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

            List<TruckDto> res = truckDBService.getTruckByCompany(user.getCompany())
                    .stream().map(x -> {TruckDto dto = new TruckDto();
                        dto.setModel(x.getModel());
                        dto.setMass(x.getTruck_mass());
                        dto.setLicensePlate(x.getRegistration_number());
                        return dto;})
                    .toList();


            return ResponseEntity.ok(res);

    }

    @GetMapping("/getTrailers")
    public ResponseEntity<List<TrailerDto>> getTrailers(@RequestHeader("Authorization") String authorizationHeader) {


        User user = userManagementService.getUserByAuthorizationHeader(authorizationHeader);

        List<TrailerDto> res = trailerDBService.getTrailerByCompany(user.getCompany())
                .stream().map(x -> {TrailerDto dto = new TrailerDto();
                    dto.setDismount(x.is_detachable());
                    dto.setVolume(x.getZ());
                    dto.setHeight(x.getY());
                    dto.setWidth(x.getX());
                    dto.setMaxMass(x.getMax_payload());
                    dto.setMass(x.getTrailer_mass());
                    return dto;})
                .toList();


        return ResponseEntity.ok(res);

    }



}
