package org.example.dao;


import org.example.model.Company;
import org.example.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO{

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Role> getRoles() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Role", Role.class).list();
    }

    @Override
    public void saveRole(Role role) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(role);
    }


    @Override
    public Role getRole(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Role role = currentSession.get(Role.class, id);
        return role;
    }
    @Override
    public void deleteRole(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Role role = currentSession.get(Role.class, id);
        currentSession.remove(role);
    }

    @Override
    public void updateRole(Role role) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(role);
    }

    @Override
    public Role findRoleByName(String role_name) {
        Session currentSession = sessionFactory.getCurrentSession();
        String hql = "FROM Role r WHERE r.role_name = :role_name";
        Query<Role> query = currentSession.createQuery(hql, Role.class);
        query.setParameter("role_name", role_name);
        List<Role> role = query.getResultList();
        return role.isEmpty() ? null : role.get(0);
    }
}
