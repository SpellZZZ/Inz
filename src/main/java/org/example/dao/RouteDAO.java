package org.example.dao;


import org.example.model.Commission;
import org.example.model.Route;

import java.util.List;

public interface RouteDAO {

    List<Route> getRoutes();

    void saveRoute(Route route);

    Route getRoute(int id);

    void deleteRoute(int id);

    void updateRoute(Route route);
}