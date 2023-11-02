package org.example.dao;

import org.example.model.Company;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CompanyDAOImpl implements CompanyDAO{

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Company> getCompany() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Company", Company.class).list();

    }

    @Override
    public void saveCompany(Company company) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(company);

    }

    @Override
    public Company getCompany(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Company company = currentSession.get(Company.class, id);
        return company;
    }

    @Override
    public void deleteCompany(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Company company = currentSession.get(Company.class, id);

    }

    @Override
    public Company getCompanyByName(String company_name) {
        Session currentSession = sessionFactory.getCurrentSession();
        String hql = "FROM Company u WHERE u.company_name = :company_name";
        Query<Company> query = currentSession.createQuery(hql, Company.class);
        query.setParameter("company_name", company_name);
        List<Company> company_ = query.getResultList();
        return company_.isEmpty() ? null : company_.get(0);

    }

    @Override
    public void updateCompany(Company company) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(company);

    }
}
