package org.example.dao;

import org.example.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommissionDAOImpl implements CommissionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Commission> getCommissions() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Commission", Commission.class).list();
    }

    @Override
    public void saveCommission(Commission commission) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(commission);
    }

    @Override
    public Commission getCommission(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Commission.class, id);
    }

    @Override
    public void deleteCommission(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Commission commission = currentSession.get(Commission.class, id);
        currentSession.remove(commission);
    }

    @Override
    public void updateCommission(Commission commission) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(commission);
    }

    @Override
    public List<Commission> getCommissionByUser(User user) {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = "FROM Commission c WHERE c.user = :user";
        Query<Commission> query = currentSession.createQuery(hql, Commission.class);
        query.setParameter("user", user);
        List<Commission> commissions = query.getResultList();
        return commissions;

    }

    @Override
    public List<Commission> getCommissionByRoute(Route route) {
        Session currentSession = sessionFactory.getCurrentSession();

        String hql = "FROM Commission c WHERE c.route = :route";
        Query<Commission> query = currentSession.createQuery(hql, Commission.class);
        query.setParameter("route", route);
        List<Commission> commissions = query.getResultList();
        return commissions;
    }

}