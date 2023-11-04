package org.example.service.dbService;

import org.example.model.Company;
import org.example.model.Role;

import java.util.List;

public interface RoleDBService {

    public List<Role> getRole();
    public void  saveRole(Role role);
    public Role getRole(int id);
    public void deleteRole(int id);
    public Role getRoleByName(String name);
    public void updateRole(Role role);
}
