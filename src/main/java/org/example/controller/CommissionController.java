package org.example.controller;


import org.example.dao.CommissionDAO;
import org.example.service.dbService.*;
import org.example.service.managementService.CompanyManagementService;
import org.example.service.managementService.UserManagementService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/commission")
public class CommissionController {

    private final UserManagementService userManagementService;
    private final UserDBService userDBService;
    private final JwtTokenUtil jwtTokenUtil;
    private final CompanyDBService companyDBService;
    private final CompanyManagementService companyManagementService;
    private final CommissionDBService commissionDAO;
    private final RouteDBService routeDBService;
    private final AddressDBService addressDBService;

    @Autowired
    public CommissionController(UserManagementService userManagementService,
                                UserDBService userDBService,
                                JwtTokenUtil jwtTokenUtil,
                                CompanyDBService companyDBService,
                                CompanyManagementService companyManagementService,
                                CommissionDBService commissionDAO,
                                RouteDBService routeDBService,
                                AddressDBService addressDBService) {
        this.userManagementService = userManagementService;
        this.userDBService = userDBService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.companyDBService = companyDBService;
        this.companyManagementService = companyManagementService;
        this.commissionDAO = commissionDAO;
        this.routeDBService = routeDBService;
        this.addressDBService = addressDBService;
    }


}
