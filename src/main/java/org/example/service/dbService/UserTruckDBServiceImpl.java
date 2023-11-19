package org.example.service.dbService;

import org.example.dao.UserTruckDAO;
import org.example.model.User_Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserTruckDBServiceImpl implements UserTruckDBService {

    @Autowired
    private UserTruckDAO userTruckDAO;

    @Override
    @Transactional
    public List<User_Truck> getUserTrucks() {
        return userTruckDAO.getUserTrucks();
    }

    @Override
    @Transactional
    public void saveUserTruck(User_Truck userTruck) {
        userTruckDAO.saveUserTruck(userTruck);
    }

    @Override
    @Transactional
    public User_Truck getUserTruckById(int id) {
        return userTruckDAO.getUserTruckById(id);
    }

    @Override
    @Transactional
    public void deleteUserTruck(int id) {
        userTruckDAO.deleteUserTruck(id);
    }

    @Override
    @Transactional
    public void updateUserTruck(User_Truck userTruck) {
        userTruckDAO.updateUserTruck(userTruck);
    }
}
