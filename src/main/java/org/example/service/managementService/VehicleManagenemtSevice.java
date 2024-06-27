package org.example.service.managementService;

import org.example.dto.BindDriverTruckTrailerDto;
import org.example.dto.BindedTrucksDto;
import org.example.dto.TrailerDto;
import org.example.dto.TruckDto;
import org.example.model.*;

import java.util.List;

public interface VehicleManagenemtSevice {
    public Truck createTruck(User user, TruckDto truckAddDto);
    public Trailer createTrailer(User user, TrailerDto trailerAddDto);
    public void bindDriverTruckTrailer(User user, Truck truck, Trailer trailer);
    public User_Truck getUserTruckRel(BindDriverTruckTrailerDto bindDriverTruckTrailerDto);
    public Truck_Trailer getTruckTrailerRel(BindDriverTruckTrailerDto bindDriverTruckTrailerDto, User_Truck userTruck);
    public List<User> getUnbindedDrivers(User user);
    public List<BindedTrucksDto> getBindedTrucks(User user);

}
