package org.example.dao;

import org.example.model.Company;
import org.example.model.Trailer;
import org.example.model.Truck;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TruckDAOImpl implements TruckDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Truck> getTrucks() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Truck", Truck.class).list();
    }

    @Override
    public void saveTruck(Truck truck) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(truck);
    }

    @Override
    public Truck getTruck(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Truck.class, id);
    }

    @Override
    public void deleteTruck(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Truck truck = currentSession.get(Truck.class, id);
        currentSession.remove(truck);
    }

    @Override
    public Truck getTruckByVin(String vin) {
        Session currentSession = sessionFactory.getCurrentSession();
        String hql = "FROM Truck t WHERE t.vin = :vin";
        Query<Truck> query = currentSession.createQuery(hql, Truck.class);
        query.setParameter("vin", vin);
        List<Truck> trucks = query.getResultList();
        return trucks.isEmpty() ? null : trucks.get(0);
    }

    @Override
    public void updateTruck(Truck truck) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(truck);
    }

    @Override
    public List<Truck> getTruckByCompany(Company company) {
        Session currentSession = sessionFactory.getCurrentSession();

        String hql = "FROM Truck t WHERE t.company = :company";
        Query<Truck> query = currentSession.createQuery(hql, Truck.class);
        query.setParameter("company", company);
        List<Truck> trucks = query.getResultList();
        return trucks;
    }
}