package org.example.dao;

import org.example.model.User_Truck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserTruckDAOImpl implements UserTruckDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User_Truck> getUserTrucks() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from User_Truck", User_Truck.class).list();
    }

    @Override
    public void saveUserTruck(User_Truck userTruck) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(userTruck);
    }

    @Override
    public User_Truck getUserTruckById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(User_Truck.class, id);
    }

    @Override
    public void deleteUserTruck(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        User_Truck userTruck = currentSession.get(User_Truck.class, id);
        if (userTruck != null) {
            currentSession.remove(userTruck);
        }
    }

    @Override
    public void updateUserTruck(User_Truck userTruck) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(userTruck);
    }
}