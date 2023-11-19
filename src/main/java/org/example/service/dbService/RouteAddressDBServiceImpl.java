package org.example.service.dbService;

import org.example.dao.RouteAddressDAO;
import org.example.model.Route_Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RouteAddressDBServiceImpl implements RouteAddressDBService {

    @Autowired
    private RouteAddressDAO routeAddressDAO;

    @Override
    @Transactional
    public List<Route_Address> getRouteAddresses() {
        return routeAddressDAO.getRouteAddresses();
    }

    @Override
    @Transactional
    public void saveRouteAddress(Route_Address routeAddress) {
        routeAddressDAO.saveRouteAddress(routeAddress);
    }

    @Override
    @Transactional
    public Route_Address getRouteAddressById(int id) {
        return routeAddressDAO.getRouteAddressById(id);
    }

    @Override
    @Transactional
    public void deleteRouteAddress(int id) {
        routeAddressDAO.deleteRouteAddress(id);
    }

    @Override
    @Transactional
    public void updateRouteAddress(Route_Address routeAddress) {
        routeAddressDAO.updateRouteAddress(routeAddress);
    }
}
