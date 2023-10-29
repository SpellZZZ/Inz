package org.example.dao;

import org.example.model.User;

import java.util.List;

public interface UserDAO {
    public List<User> getUsers();
    public void  saveUser(User user);
    public User getUser(int id);
    public void deleteUser(int id);
    public User getUserByUserName(String username);
    public void updateUser(User user);
}
