package org.example.service.dbService;


import jakarta.transaction.Transactional;
import org.example.dao.RouteDAO;
import org.example.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteDBServiceImpl implements RouteDBService {

    private final RouteDAO routeDAO;

    @Autowired
    public RouteDBServiceImpl(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    @Override
    @Transactional
    public List<Route> getRoutes() {
        return routeDAO.getRoutes();
    }

    @Override
    @Transactional
    public void saveRoute(Route route) {
        routeDAO.saveRoute(route);
    }

    @Override
    @Transactional
    public Route getRoute(int id) {
        return routeDAO.getRoute(id);
    }

    @Override
    @Transactional
    public void deleteRoute(int id) {
        routeDAO.deleteRoute(id);
    }

    @Override
    @Transactional
    public void updateRoute(Route route) {
        routeDAO.updateRoute(route);
    }

}
