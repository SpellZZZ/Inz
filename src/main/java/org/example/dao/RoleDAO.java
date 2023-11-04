package org.example.dao;

import org.example.model.Role;

import java.util.List;

public interface RoleDAO {
    public List<Role> getRoles();
    public void saveRole(Role role);
    public Role getRole(int id);
    public void deleteRole(int id);
    public void updateRole(Role role);
    public Role findRoleByName(String string);
}
