package org.example.service.dbService;

import jakarta.transaction.Transactional;
import org.example.dao.RouteDAO;
import org.example.dao.RouteTruckDAO;
import org.example.model.Route_Truck;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteTruckDBServiceImpl implements RouteTruckDBService{


    private final RouteTruckDAO routeTruckDAO;

    @Autowired
    public RouteTruckDBServiceImpl(RouteTruckDAO routeTruckDAO) {
        this.routeTruckDAO = routeTruckDAO;
    }

    @Override
    @Transactional
    public List<Route_Truck> getRouteTruck() {
        return routeTruckDAO.getRouteTruck();
    }

    @Override
    public void saveRouteTruck(Route_Truck routeTruck) {
        routeTruckDAO.saveRouteTruck(routeTruck);
    }

    @Override
    @Transactional
    public Route_Truck getRouteTruckById(int id) {
        return routeTruckDAO.getRouteTruckById(id);
    }

    @Override
    @Transactional
    public void deleteRouteTruck(int id) {
        routeTruckDAO.deleteRouteTruck(id);
    }

    @Override
    @Transactional
    public void updateRouteTruck(Route_Truck routeTruck) {
        routeTruckDAO.updateRouteTruck(routeTruck);
    }

    @Override
    @Transactional
    public List<Route_Truck> getRouteTruckByUser(User user) {
        return routeTruckDAO.getRouteTruckByUser(user);
    }
}
