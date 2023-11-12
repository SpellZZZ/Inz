package org.example.service.dbService;

import jakarta.transaction.Transactional;
import org.example.dao.TruckDAO;
import org.example.model.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckDBServiceImpl implements TruckDBService {

    @Autowired
    private TruckDAO truckDAO;

    @Override
    @Transactional
    public List<Truck> getTrucks() {
        return truckDAO.getTrucks();
    }

    @Override
    @Transactional
    public void saveTruck(Truck truck) {
        truckDAO.saveTruck(truck);
    }

    @Override
    @Transactional
    public Truck getTruck(int id) {
        return truckDAO.getTruck(id);
    }

    @Override
    @Transactional
    public void deleteTruck(int id) {
        truckDAO.deleteTruck(id);
    }

    @Override
    @Transactional
    public Truck getTruckByVin(String vin) {
        return truckDAO.getTruckByVin(vin);
    }

    @Override
    @Transactional
    public void updateTruck(Truck truck) {
        truckDAO.updateTruck(truck);
    }
}