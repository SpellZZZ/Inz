package org.example.service.dbService;

import org.example.model.User_Truck;

import java.util.List;

public interface UserTruckDBService {
    List<User_Truck> getUserTrucks();

    void saveUserTruck(User_Truck userTruck);

    User_Truck getUserTruckById(int id);

    void deleteUserTruck(int id);

    void updateUserTruck(User_Truck userTruck);
}
