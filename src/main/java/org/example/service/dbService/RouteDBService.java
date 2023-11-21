package org.example.service.dbService;

import org.example.model.Company;
import org.example.model.Route;
import org.example.model.User;

import java.util.List;

public interface RouteDBService {

    List<Route> getRoutes();

    void saveRoute(Route route);

    Route getRoute(int id);

    void deleteRoute(int id);

    void updateRoute(Route route);
    List<Route> getRouteByCompany(Company company);
    List<Route> getRouteByUser(User user);
}