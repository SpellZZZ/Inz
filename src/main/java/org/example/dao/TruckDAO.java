package org.example.dao;

import org.example.model.Truck;

import java.util.List;

public interface TruckDAO {
    List<Truck> getTrucks();

    void saveTruck(Truck truck);

    Truck getTruck(int id);

    void deleteTruck(int id);

    Truck getTruckByVin(String vin);

    void updateTruck(Truck truck);
}