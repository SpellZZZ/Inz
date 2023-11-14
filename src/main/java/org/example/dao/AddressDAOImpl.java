package org.example.dao;

import org.example.model.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressDAOImpl implements AddressDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Address> getAddresses() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Address", Address.class).list();
    }

    @Override
    public void saveAddress(Address address) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(address);
    }

    @Override
    public Address getAddress(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Address.class, id);
    }

    @Override
    public void deleteAddress(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Address address = currentSession.get(Address.class, id);
        currentSession.remove(address);
    }

    @Override
    public void updateAddress(Address address) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(address);
    }

    @Override
    public Address findAddressByZipCode(String zipCode) {
        Session currentSession = sessionFactory.getCurrentSession();
        String hql = "FROM Address a WHERE a.zip_code = :zipCode";
        Query<Address> query = currentSession.createQuery(hql, Address.class);
        query.setParameter("zipCode", zipCode);
        List<Address> addresses = query.getResultList();
        return addresses.isEmpty() ? null : addresses.get(0);
    }
}