package org.example.service.managementService;

import org.example.dto.CommissionDto;
import org.example.model.Address;

public interface CommissionManagementService {
    public void commissionCreate(CommissionDto commissionDto,
                                 Address addressStart,
                                 Address addressEnd,
                                 String authorizationHeader);
    public Address addressStartCreate(CommissionDto commissionDto);
    public Address addressEndCreate(CommissionDto commissionDto);

}
