package org.example.service.dbService;

import org.example.model.Truck;

import java.util.List;

public interface TruckDBService {
    List<Truck> getTrucks();

    void saveTruck(Truck truck);

    Truck getTruck(int id);

    void deleteTruck(int id);

    Truck getTruckByVin(String vin);

    void updateTruck(Truck truck);
}