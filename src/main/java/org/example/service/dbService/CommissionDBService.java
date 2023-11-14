package org.example.service.dbService;

import org.example.model.Commission;

import java.util.List;

public interface CommissionDBService {
    List<Commission> getCommissions();

    void saveCommission(Commission commission);

    Commission getCommission(int id);

    void deleteCommission(int id);

    void updateCommission(Commission commission);

}
