package org.example.dao;

import org.example.model.Route_Address;

import java.util.List;

public interface RouteAddressDAO {
    List<Route_Address> getRouteAddresses();

    void saveRouteAddress(Route_Address routeAddress);

    Route_Address getRouteAddressById(int id);

    void deleteRouteAddress(int id);

    void updateRouteAddress(Route_Address routeAddress);
}
