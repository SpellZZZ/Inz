package test;

import org.example.controller.RouteController;
import org.example.model.Company;
import org.example.service.JwtAuthService;
import org.example.service.dbService.RoleDBService;
import org.example.service.managementService.CompanyManagementServiceImpl;
import org.example.service.managementService.UserManagementService;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.dbService.UserDBService;
import org.example.service.managementService.UserManagementServiceImpl;
import org.example.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UnitTest {

    @Mock
    private UserDBService userDBService;
    @Mock
    private JwtAuthService jwtAuthService;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private RoleDBService roleDBService;


    private UserManagementService userManagementService;



    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userManagementService = new UserManagementServiceImpl(userDBService, jwtTokenUtil, jwtAuthService, roleDBService);
    }

    @Test
    void testGetUserRole() {

        String userName = "test";
        Role role = new Role();
        role.setRole_name("Uzytkownik");

        User user = new User();
        user.setRole(role);

        when(userDBService.getUserByUserName(userName)).thenReturn(user);

        String actualRole = userManagementService.getUserRole(userName);

        assertEquals("Uzytkownik", actualRole);
    }

    @Test
    void checkDateTest(){
        String d1 = "2024-03-11";
        String d2 = "2024-03-17";

        boolean result = RouteController.validateDate(d1, d2);

        assertTrue(result);

    }


}
