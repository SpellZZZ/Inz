package org.example.service.dbService;

import jakarta.transaction.Transactional;
import org.example.dao.BrandDAO;
import org.example.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandDBServiceImpl implements BrandDBService {

    @Autowired
    private BrandDAO brandDAO;

    @Override
    @Transactional
    public List<Brand> getBrands() {
        return brandDAO.getBrands();
    }

    @Override
    @Transactional
    public void saveBrand(Brand brand) {
        brandDAO.saveBrand(brand);
    }

    @Override
    @Transactional
    public Brand getBrand(int id) {
        return brandDAO.getBrand(id);
    }

    @Override
    @Transactional
    public void deleteBrand(int id) {
        brandDAO.deleteBrand(id);
    }

    @Override
    @Transactional
    public Brand getBrandByName(String brand_name) {
        return brandDAO.getBrandByName(brand_name);
    }

    @Override
    @Transactional
    public void updateBrand(Brand brand) {
        brandDAO.updateBrand(brand);
    }
}