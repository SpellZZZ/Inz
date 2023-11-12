package org.example.dao;

import org.example.model.Company;
import org.example.model.Truck;
import org.example.model.User;

import java.util.List;

public interface TruckDAO {
    List<Truck> getTrucks();

    void saveTruck(Truck truck);

    Truck getTruck(int id);

    void deleteTruck(int id);

    Truck getTruckByVin(String vin);

    void updateTruck(Truck truck);

    public List<Truck> getTruckByCompany(Company company);
}