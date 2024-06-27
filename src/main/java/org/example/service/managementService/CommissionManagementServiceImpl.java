package org.example.service.managementService;

import org.example.dto.CommissionDto;
import org.example.model.Address;
import org.example.model.Commission;
import org.example.service.dbService.AddressDBService;
import org.example.service.dbService.CommissionDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class CommissionManagementServiceImpl implements CommissionManagementService{


    private final AddressDBService addressDBService;
    private final UserManagementService userManagementService;
    private final CommissionDBService commissionDBService;

    @Autowired
    public CommissionManagementServiceImpl(AddressDBService addressDBService,
                                           CommissionDBService commissionDBService,
                                           UserManagementService userManagementService) {
        this.addressDBService = addressDBService;
        this.commissionDBService = commissionDBService;
        this.userManagementService = userManagementService;
    }


    public void commissionCreate(CommissionDto commissionDto,
                                       Address addressStart,
                                       Address addressEnd,
                                       String authorizationHeader){

        Commission commission = new Commission();

        commission.setDelivery_pickup(addressStart);
        commission.setDelivery_endpoint(addressEnd);
        commission.setDescription(commissionDto.getDescription());
        commission.setX(commissionDto.getXpackage());
        commission.setY(commissionDto.getYpackage());
        commission.setZ(commissionDto.getZpackage());
        commission.setMass(commissionDto.getMass());
        commission.setStackable(commissionDto.getStackable());
        commission.setCount(commissionDto.getCount());

        commission.setUser(userManagementService.getUserByAuthorizationHeader(authorizationHeader));
        commission.setDate_of_placement(Date.valueOf(LocalDate.now()));
        commission.setIs_loaded(false);
        commission.setIs_unloaded(false);

        commissionDBService.saveCommission(commission);
    }
    public Address addressStartCreate(CommissionDto commissionDto){
        Address address = new Address();

        address.setAddress(commissionDto.getAddressstart());
        address.setGPS_X(commissionDto.getGps_xstart());
        address.setGPS_Y(commissionDto.getGps_ystart());
        address.setHouse_number(commissionDto.getHouse_numberstart());
        address.setZip_code(commissionDto.getZip_codestart());
        address.setCity(commissionDto.getCitystart());

        addressDBService.saveAddress(address);

        return address;

    }
    public Address addressEndCreate(CommissionDto commissionDto){
        Address address = new Address();

        address.setAddress(commissionDto.getAddressend());
        address.setGPS_X(commissionDto.getGps_xend());
        address.setGPS_Y(commissionDto.getGps_yend());
        address.setHouse_number(commissionDto.getHouse_numberend());
        address.setZip_code(commissionDto.getZip_codeend());
        address.setCity(commissionDto.getCityend());

        addressDBService.saveAddress(address);

        return address;

    }


}
