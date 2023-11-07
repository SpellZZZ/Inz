package org.example.dao;

import org.example.model.Brand;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrandDAOImpl implements BrandDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Brand> getBrands() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Brand", Brand.class).list();
    }

    @Override
    public void saveBrand(Brand brand) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(brand);
    }

    @Override
    public Brand getBrand(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Brand.class, id);
    }

    @Override
    public void deleteBrand(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Brand brand = currentSession.get(Brand.class, id);
        currentSession.remove(brand);
    }

    @Override
    public Brand getBrandByName(String brand_name) {
        Session currentSession = sessionFactory.getCurrentSession();
        String hql = "FROM Brand b WHERE b.brand_name = :brand_name";
        Query<Brand> query = currentSession.createQuery(hql, Brand.class);
        query.setParameter("brand_name", brand_name);
        List<Brand> brands = query.getResultList();
        return brands.isEmpty() ? null : brands.get(0);
    }

    @Override
    public void updateBrand(Brand brand) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(brand);
    }
}