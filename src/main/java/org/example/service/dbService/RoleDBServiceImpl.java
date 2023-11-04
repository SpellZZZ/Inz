package org.example.service.dbService;


import jakarta.transaction.Transactional;
import org.example.dao.RoleDAO;
import org.example.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleDBServiceImpl implements RoleDBService{

    @Autowired
    private RoleDAO roleDAO;

    @Override
    @Transactional
    public List<Role> getRole() {
        return roleDAO.getRoles();
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleDAO.saveRole(role);
    }

    @Override
    @Transactional
    public Role getRole(int id) {
        return roleDAO.getRole(id);
    }

    @Override
    @Transactional
    public void deleteRole(int id) {
        roleDAO.deleteRole(id);
    }

    @Override
    @Transactional
    public Role getRoleByName(String name) {
        return roleDAO.findRoleByName(name);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        roleDAO.updateRole(role);
    }
}
