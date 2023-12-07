package test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.QProjectApplication;
import org.example.controller.CompanyController;
import org.example.dto.*;
import org.example.model.Commission;
import org.example.model.Truck;
import org.example.service.dbService.CommissionDBService;
import org.example.service.dbService.CompanyDBService;
import org.example.service.dbService.TruckDBService;
import org.example.service.dbService.TruckDBServiceImpl;
import org.example.service.managementService.CompanyManagementService;
import org.example.service.managementService.UserManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.MDC.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = QProjectApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TruckDBService truckDBService;

    @Autowired
    private CommissionDBService commissionDBService;


    @Test
    void authenticateTest() throws Exception {

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setLoginUsername("test");
        authenticationRequestDto.setLoginPassword("123");


        String loginUrl = "/authenticate";
        MvcResult loginResult = mockMvc.perform(post(loginUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authenticationRequestDto)))
                .andExpect(status().isOk())
                .andReturn();


        String token = "";

        AuthenticateResponseDto response = new ObjectMapper()
                .readValue(loginResult.getResponse().getContentAsString(), AuthenticateResponseDto.class);
        if (response != null) {
            token = response.getToken();
        }


        assertNotNull(token, "Token is not null");


    }
    @Test
    void createCompanyTest() throws Exception {

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setLoginUsername("test");
        authenticationRequestDto.setLoginPassword("123");


        String loginUrl = "/authenticate";
        MvcResult loginResult = mockMvc.perform(post(loginUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authenticationRequestDto)))
                .andExpect(status().isOk())
                .andReturn();


        String token = "";

        AuthenticateResponseDto response = new ObjectMapper()
                .readValue(loginResult.getResponse().getContentAsString(), AuthenticateResponseDto.class);
        if (response != null) {
            token = response.getToken();
        }



        CompanyFormDto companyFormDto = new CompanyFormDto();
        companyFormDto.setCompany_name("Test Company");
        companyFormDto.setCompany_nip("12345");


        mockMvc.perform(post("/companyCreate")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(companyFormDto)))
                .andExpect(status().isOk());


        mockMvc.perform(delete("/companyDelete")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }


    @Test
    void addAndDeleteTruckTest() throws Exception {
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setLoginUsername("test");
        authenticationRequestDto.setLoginPassword("123");

        String loginUrl = "/authenticate";
        MvcResult loginResult = mockMvc.perform(post(loginUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authenticationRequestDto)))
                .andExpect(status().isOk())
                .andReturn();

        String token = "";

        AuthenticateResponseDto response = new ObjectMapper()
                .readValue(loginResult.getResponse().getContentAsString(), AuthenticateResponseDto.class);
        if (response != null) {
            token = response.getToken();
        }

        TruckDto truckDto = new TruckDto();
        truckDto.setMass(100);
        truckDto.setVin("vinTest");

        mockMvc.perform(post("/addTruck")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(truckDto)))
                .andExpect(status().isOk());


        List<Truck> trucks = truckDBService.getTrucks();
        assertTrue(trucks.size() > 0, "Dodano ciężarówkę");

        BindDriverTruckTrailerDto bindDriverTruckTrailerDto = new BindDriverTruckTrailerDto();
        bindDriverTruckTrailerDto.setTruck_id(truckDBService.getTruckByVin("vinTest").getTruck_id());

        mockMvc.perform(post("/deleteTruck")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bindDriverTruckTrailerDto)))
                .andExpect(status().isOk());
    }




    @Test
    void createCommissionAndCheckUserCommissions() throws Exception {
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setLoginUsername("test");
        authenticationRequestDto.setLoginPassword("123");

        String loginUrl = "/authenticate";
        MvcResult loginResult = mockMvc.perform(post(loginUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authenticationRequestDto)))
                .andExpect(status().isOk())
                .andReturn();

        String token = "";

        AuthenticateResponseDto response = new ObjectMapper()
                .readValue(loginResult.getResponse().getContentAsString(), AuthenticateResponseDto.class);
        if (response != null) {
            token = response.getToken();
        }


        CommissionDto commissionDto = new CommissionDto();


        mockMvc.perform(post("/commissionCreate")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commissionDto)))
                .andExpect(status().isOk());





        MvcResult userCommissionsResult = mockMvc.perform(MockMvcRequestBuilders.get("/getUserCommissions")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Commission> commissions = objectMapper.readValue(
                userCommissionsResult.getResponse().getContentAsString(),
                new TypeReference<List<Commission>>() {}
        );

        commissionDBService.deleteCommission(commissions.get(0).getCommission_id());

    }







}
