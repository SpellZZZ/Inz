package org.example.service.dbService;


import jakarta.transaction.Transactional;
import org.example.dao.UserDAO;
import org.example.model.Company;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDBServiceImpl implements UserDBService {
    @Autowired
    private UserDAO userDAO;
    @Override
    @Transactional
    public List<User> getUsers() {
        return userDAO.getUsers();
    }
    @Override
    @Transactional
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }
    @Override
    @Transactional
    public User getUser(int id) {
        return userDAO.getUser(id);
    }
    @Override
    @Transactional
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
    @Override
    @Transactional
    public User getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }
    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
    @Override
    @Transactional
    public void userUpdate(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public List<User> getUserByCompany(Company company){
        return userDAO.getUserByCompany(company);
    }
}
