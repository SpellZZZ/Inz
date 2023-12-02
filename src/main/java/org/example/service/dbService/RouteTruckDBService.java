package org.example.service.dbService;

import org.example.model.Route_Truck;
import org.example.model.Truck;
import org.example.model.User;

import java.util.List;

public interface RouteTruckDBService {

        List<Route_Truck> getRouteTruck();

        void saveRouteTruck(Route_Truck routeTruck);

        Route_Truck getRouteTruckById(int id);

        void deleteRouteTruck(int id);

        void updateRouteTruck(Route_Truck routeTruck);

        List<Route_Truck> getRouteTruckByUser(User user);

        List<Route_Truck> getRouteTruckByTruck(Truck truck);
}
