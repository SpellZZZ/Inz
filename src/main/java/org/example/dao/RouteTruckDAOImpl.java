package org.example.dao;

import org.example.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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

    @Override
    public List<Route_Truck> getRouteTruckByUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        String hql = "FROM Route_Truck t WHERE t.user_id = :user_id";
        Query<Route_Truck> query = currentSession.createQuery(hql, Route_Truck.class);
        query.setParameter("user_id", user);
        List<Route_Truck> routeTrucks = query.getResultList();
        return routeTrucks;
    }

    @Override
    public List<Route_Truck> getRouteTruckByTruck(Truck truck) {
        Session currentSession = sessionFactory.getCurrentSession();

        String hql = "FROM Route_Truck t WHERE t.truck_id = :truck_id";
        Query<Route_Truck> query = currentSession.createQuery(hql, Route_Truck.class);
        query.setParameter("truck_id", truck);
        List<Route_Truck> routeTrucks = query.getResultList();
        return routeTrucks;
    }

    @Override
    public List<Route_Truck> getRouteTruckByRoute(Route route) {
        Session currentSession = sessionFactory.getCurrentSession();

        String hql = "FROM Route_Truck t WHERE t.route_id = :route_id";
        Query<Route_Truck> query = currentSession.createQuery(hql, Route_Truck.class);
        query.setParameter("route_id", route);
        List<Route_Truck> routeTrucks = query.getResultList();
        return routeTrucks;
    }
}
