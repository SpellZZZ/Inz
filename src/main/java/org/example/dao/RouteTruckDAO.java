package org.example.dao;

import org.example.model.*;

import java.util.List;

public interface RouteTruckDAO {
    List<Route_Truck> getRouteTruck();

    void saveRouteTruck(Route_Truck routeTruck);

    Route_Truck getRouteTruckById(int id);

    void deleteRouteTruck(int id);

    void updateRouteTruck(Route_Truck routeTruck);


    List<Route_Truck> getRouteTruckByUser(User user);

    List<Route_Truck> getRouteTruckByTruck(Truck truck);

    List<Route_Truck> getRouteTruckByRoute(Route route);
}