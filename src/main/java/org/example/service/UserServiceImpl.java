package org.example.service;


import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.transaction.Transactional;
import org.example.dao.UserDAO;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
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
    public User getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }
}
