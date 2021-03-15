package org.upgrad.upstac.testrequests.lab;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.upgrad.upstac.testutils.TestData.getMockedDoctorCredentials;
import static org.upgrad.upstac.testutils.TestData.getMockedTesterCredentials;


@SpringBootTest
@AutoConfigureMockMvc
class LabRequestControllerWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    UserService userService;


    @SpyBean
    private UserLoggedInService userLoggedInService;





    @Test
    public void calling_to_be_tested_should_retrieve_all_test_requests_ready_for_test() throws Exception{

        mockMvc.perform(get("/api/labrequests/to-be-tested").with(getMockedTesterCredentials()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()", greaterThan(2)));
    }
    @Test
    public void calling_api_getForTester_should_retrieve_all_tests_done_by_lab_tester() throws Exception{

        mockMvc.perform(get("/api/labrequests").with(getMockedTesterCredentials()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()", greaterThan(1)));

        Mockito.verify(userLoggedInService,Mockito.atLeastOnce()).getLoggedInUser();
    }

}