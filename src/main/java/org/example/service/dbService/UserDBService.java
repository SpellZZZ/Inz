package org.example.service.dbService;


import org.example.model.Company;
import org.example.model.User;

import java.util.List;

public interface UserDBService {
    public List<User> getUsers();
    public void  saveUser(User user);
    public User getUser(int id);
    public void deleteUser(int id);
    public User getUserByUserName(String userName);
    public User getUserByEmail(String email);
    public void userUpdate(User user);
    public List<User> getUserByCompany(Company company);

}
