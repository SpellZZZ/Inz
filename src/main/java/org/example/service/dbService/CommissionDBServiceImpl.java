package org.example.service.dbService;


import jakarta.transaction.Transactional;
import org.example.dao.CommissionDAO;
import org.example.model.Commission;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommissionDBServiceImpl implements CommissionDBService{

    @Autowired
    private CommissionDAO commissionDAO;

    @Override
    @Transactional
    public List<Commission> getCommissions() {
        return commissionDAO.getCommissions();
    }

    @Override
    @Transactional
    public void saveCommission(Commission commission) {
        commissionDAO.saveCommission(commission);
    }

    @Override
    @Transactional
    public Commission getCommission(int id) {
        return commissionDAO.getCommission(id);
    }

    @Override
    @Transactional
    public void deleteCommission(int id) {
        commissionDAO.deleteCommission(id);
    }

    @Override
    @Transactional
    public void updateCommission(Commission commission) {
        commissionDAO.updateCommission(commission);
    }

    @Override
    @Transactional
    public List<Commission> getCommissionByUser(User user) {
        return commissionDAO.getCommissionByUser(user);
    }
}
