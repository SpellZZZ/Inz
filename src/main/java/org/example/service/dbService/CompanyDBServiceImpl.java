package org.example.service.dbService;


import jakarta.transaction.Transactional;
import org.example.dao.CompanyDAO;
import org.example.dao.UserDAO;
import org.example.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyDBServiceImpl implements CompanyDBService {

    @Autowired
    private CompanyDAO companyDAO;


    @Override
    @Transactional
    public List<Company> getCompany() {
        return companyDAO.getCompany();
    }

    @Override
    @Transactional
    public void saveCompany(Company company) {
         companyDAO.saveCompany(company);
    }

    @Override
    @Transactional
    public Company getCompany(int id) {
        return companyDAO.getCompany(id);
    }

    @Override
    @Transactional
    public void deleteCompany(int id) {
        companyDAO.deleteCompany(id);
    }

    @Override
    @Transactional
    public Company getCompanyByName(String name) {
        return companyDAO.getCompanyByName(name);
    }

    @Override
    @Transactional
    public void updateCompany(Company company) {
        companyDAO.updateCompany(company);
    }
}
