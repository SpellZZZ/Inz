package org.example.dao;

import org.example.model.Route_Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RouteAddressDAOImpl implements RouteAddressDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Route_Address> getRouteAddresses() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Route_Address", Route_Address.class).list();
    }

    @Override
    public void saveRouteAddress(Route_Address routeAddress) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(routeAddress);
    }

    @Override
    public Route_Address getRouteAddressById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Route_Address.class, id);
    }

    @Override
    public void deleteRouteAddress(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Route_Address routeAddress = currentSession.get(Route_Address.class, id);
        if (routeAddress != null) {
            currentSession.remove(routeAddress);
        }
    }

    @Override
    public void updateRouteAddress(Route_Address routeAddress) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(routeAddress);
    }
}
