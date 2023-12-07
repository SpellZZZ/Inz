package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.QProjectApplication;
import org.example.controller.CompanyController;
import org.example.dto.AuthenticateResponseDto;
import org.example.dto.AuthenticationRequestDto;
import org.example.dto.CompanyFormDto;
import org.example.service.dbService.CompanyDBService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

}
