package org.example.dao;

import org.example.model.Company;
import org.example.model.User;

import java.util.List;

public interface CompanyDAO {
    public List<Company> getCompany();
    public void  saveCompany(Company company);
    public Company getCompany(int id);
    public void deleteCompany(int id);
    public Company getCompanyByName(String name);
    public void updateCompany(Company company);
}
