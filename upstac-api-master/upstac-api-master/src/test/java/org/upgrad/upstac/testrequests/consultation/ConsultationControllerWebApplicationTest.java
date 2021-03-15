package org.upgrad.upstac.testrequests.consultation;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.users.UserService;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.upgrad.upstac.testutils.TestData.getMockedDoctorCredentials;
import static org.upgrad.upstac.testutils.TestData.getMockedGovernmentAuthorityCredentials;


@SpringBootTest
@AutoConfigureMockMvc
class ConsultationControllerWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    UserService userService;


    @SpyBean
    private UserLoggedInService userLoggedInService;





    @Test
    public void calling_in_queue_should_retrieve_all_consultation_data() throws Exception{

        mockMvc.perform(get("/api/consultations/in-queue").with(getMockedDoctorCredentials()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()", greaterThan(2)));
    }
    @Test
    public void calling_api_consultations_should_retrieve_all_consultations_for_that_doctor() throws Exception{

        mockMvc.perform(get("/api/consultations").with(getMockedDoctorCredentials()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()", greaterThan(1)));

        Mockito.verify(userLoggedInService,Mockito.atLeastOnce()).getLoggedInUser();
    }

}