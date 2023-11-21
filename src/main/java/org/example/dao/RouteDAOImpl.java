package org.example.dao;

import org.example.model.Company;
import org.example.model.Route;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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

    public List<Route> getRouteByCompany(Company company) {
        Session currentSession = sessionFactory.getCurrentSession();

        String hql = "FROM Route u WHERE u.company = :company";
        Query<Route> query = currentSession.createQuery(hql, Route.class);
        query.setParameter("company", company);
        List<Route> routes = query.getResultList();
        return routes;
    }


    public List<Route> getRouteByUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        String hql = "FROM Route u WHERE u.user = :user";
        Query<Route> query = currentSession.createQuery(hql, Route.class);
        query.setParameter("user", user);
        List<Route> routes = query.getResultList();
        return routes;
    }

}