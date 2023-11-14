package org.example.dao;

import org.example.model.Route;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RouteDAOImpl implements RouteDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public RouteDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Route> getRoutes() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Route", Route.class).list();
    }

    @Override
    public void saveRoute(Route route) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(route);
    }

    @Override
    public Route getRoute(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Route.class, id);
    }

    @Override
    public void deleteRoute(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Route route = currentSession.get(Route.class, id);
        currentSession.remove(route);
    }

    @Override
    public void updateRoute(Route route) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(route);
    }
}