package org.example.dao;

import org.example.model.Company;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements  UserDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> getUsers() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from User", User.class).list();
    }

    @Override
    public void saveUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(user);
    }

    @Override
    public User getUser(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        User user = currentSession.get(User.class, id);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        User user = currentSession.get(User.class, id);
        currentSession.remove(user);
    }

    @Override
    public User getUserByUserName(String username) {
        Session currentSession = sessionFactory.getCurrentSession();
        String hql = "FROM User u WHERE u.username = :username";
        Query<User> query = currentSession.createQuery(hql, User.class);
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }
    @Override
    public User getUserByEmail(String email) {
        Session currentSession = sessionFactory.getCurrentSession();
        String hql = "FROM User u WHERE u.email = :email";
        Query<User> query = currentSession.createQuery(hql, User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void updateUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(user);
    }

    @Override
    public List<User> getUserByCompany(Company company) {
        Session currentSession = sessionFactory.getCurrentSession();

        String hql = "FROM User u WHERE u.company = :company";
        Query<User> query = currentSession.createQuery(hql, User.class);
        query.setParameter("company", company);
        List<User> users = query.getResultList();
        return users;
    }
}
