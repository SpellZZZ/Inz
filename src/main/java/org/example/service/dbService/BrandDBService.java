package org.example.service.dbService;

import org.example.model.Brand;

import java.util.List;

public interface BrandDBService {
    List<Brand> getBrands();

    void saveBrand(Brand brand);

    Brand getBrand(int id);

    void deleteBrand(int id);

    Brand getBrandByName(String brand_name);

    void updateBrand(Brand brand);
}
