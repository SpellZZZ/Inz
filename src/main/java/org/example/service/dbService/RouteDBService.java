package org.example.service.dbService;

import org.example.model.Route;

import java.util.List;

public interface RouteDBService {

    List<Route> getRoutes();

    void saveRoute(Route route);

    Route getRoute(int id);

    void deleteRoute(int id);

    void updateRoute(Route route);
}