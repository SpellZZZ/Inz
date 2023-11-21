package org.example.dao;

import org.example.model.*;

import java.util.List;

public interface CommissionDAO {

    List<Commission> getCommissions();

    void saveCommission(Commission commission);

    Commission getCommission(int id);

    void deleteCommission(int id);

    void updateCommission(Commission commission);
    public List<Commission> getCommissionByUser(User user);
    public List<Commission> getCommissionByRoute(Route route);
}