package org.example.dao;

import org.example.model.Route_Address;
import org.example.model.Route_Truck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class RouteTruckDAOImpl implements RouteTruckDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Route_Truck> getRouteTruck() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Route_Truck", Route_Truck.class).list();
    }

    @Override
    public void saveRouteTruck(Route_Truck routeTruck) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(routeTruck);
    }

    @Override
    public Route_Truck getRouteTruckById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Route_Truck.class, id);
    }

    @Override
    public void deleteRouteTruck(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Route_Truck routeTruck = currentSession.get(Route_Truck.class, id);
        if (routeTruck != null) {
            currentSession.remove(routeTruck);
        }
    }

    @Override
    public void updateRouteTruck(Route_Truck routeTruck) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(routeTruck);
    }
}
