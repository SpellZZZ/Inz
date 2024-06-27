package org.example.service.managementService;


import org.example.dto.BindDriverTruckTrailerDto;
import org.example.dto.BindedTrucksDto;
import org.example.dto.TrailerDto;
import org.example.dto.TruckDto;
import org.example.model.*;

import org.example.service.dbService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehicleManagenemtSeviceImpl implements VehicleManagenemtSevice{



    private final TruckTrailerDBService truckTrailerDBService;
    private final UserTruckDBService userTruckDBService;
    private final UserDBService userDBService;
    private final TruckDBService truckDBService;
    private final TrailerDBService trailerDBService;

    @Autowired
    public VehicleManagenemtSeviceImpl(
            TruckTrailerDBService truckTrailerDBService,
            UserTruckDBService userTruckDBService,
            UserDBService userDBService,
            TruckDBService truckDBService,
            TrailerDBService trailerDBService
    ) {
        this.truckTrailerDBService = truckTrailerDBService;
        this.userTruckDBService = userTruckDBService;
        this.userDBService = userDBService;
        this.truckDBService = truckDBService;
        this.trailerDBService = trailerDBService;

    }

    public Truck createTruck(User user, TruckDto truckAddDto) {
        Truck truck = new Truck();
        truck.setCompany(user.getCompany());
        truck.setModel(truckAddDto.getModel());
        truck.setTruck_mass(truckAddDto.getMass());
        truck.setRegistration_number(truckAddDto.getLicensePlate());
        return truck;
    }


    public Trailer createTrailer(User user, TrailerDto trailerAddDto) {
        Trailer trailer = new Trailer();

        trailer.setCompany(user.getCompany());
        trailer.set_detachable(trailerAddDto.isDismount());
        trailer.setTrailer_mass(trailerAddDto.getMass());
        trailer.setMax_payload(trailerAddDto.getMaxMass());
        trailer.setX(trailerAddDto.getWidth());
        trailer.setY(trailerAddDto.getHeight());
        trailer.setZ(trailerAddDto.getVolume());

        return trailer;
    }


    public void bindDriverTruckTrailer(User user, Truck truck, Trailer trailer){
        User_Truck userTruck = new User_Truck();
        userTruck.setTruck(truck);
        userTruck.setUser(user);
        userTruckDBService.saveUserTruck(userTruck);

        Truck_Trailer truckTrailer = new Truck_Trailer();
        truckTrailer.setTrailer(trailer);
        truckTrailer.setTruck(truck);
        truckTrailer.setUser_id(user.getUser_id());
        truckTrailerDBService.saveTruckTrailer(truckTrailer);
    }

    public User_Truck getUserTruckRel(BindDriverTruckTrailerDto bindDriverTruckTrailerDto){
        return  userTruckDBService.getUserTrucks().stream()
                .filter(x->
                        x.getUser().getUser_id()==bindDriverTruckTrailerDto.getUser_id()
                                && x.getTruck().getTruck_id()== bindDriverTruckTrailerDto.getTruck_id()).findFirst().get();
    }


    public Truck_Trailer getTruckTrailerRel(BindDriverTruckTrailerDto bindDriverTruckTrailerDto, User_Truck userTruck){
        return truckTrailerDBService.getTruckTrailers().stream()
                .filter(x->
                        x.getTruck().getTruck_id()== bindDriverTruckTrailerDto.getTruck_id()
                                && x.getTrailer().getTrailer_id() == bindDriverTruckTrailerDto.getTrailer_id()
                                && x.getUser_id() == userTruck.getUser().getUser_id()).findFirst().get();

    }

    public List<User> getUnbindedDrivers(User user){
        return  userDBService.getUserByCompany(user.getCompany()).stream().filter(
                        x -> x.getRole().getRole_name().equals("Kierowca")
                                && x.getTrucks().isEmpty())
                .collect(Collectors.toList());

    }


    public List<BindedTrucksDto> getBindedTrucks(User user){


        List<BindedTrucksDto> res = new ArrayList<>();

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



        Set<String> unique = new HashSet<>();

        for(BindedTrucksDto s : trucks){
            if(unique.add(s.getToString())){
                res.add(s);
            }
        }

        return res;


    }


}
