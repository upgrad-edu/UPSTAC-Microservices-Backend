package org.upgrad.upstac.testrequests.authority;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.testrequests.models.CreateTestRequest;
import org.upgrad.upstac.testrequests.models.RequestStatus;
import org.upgrad.upstac.users.UserService;
import org.upgrad.upstac.users.models.Gender;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.upgrad.upstac.testutils.JsonHelper.getAsJsonString;
import static org.upgrad.upstac.testutils.TestData.getMockedGovernmentAuthorityCredentials;


@SpringBootTest
@AutoConfigureMockMvc
class GovernmentAuthorityControllerWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    UserService userService;


    @SpyBean
    private UserLoggedInService userLoggedInService;





    @Test
    public void calling_getAll_should_retrieve_all_data() throws Exception{

        mockMvc.perform(get("/api/government/all-requests").with(getMockedGovernmentAuthorityCredentials()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()", greaterThan(2)));
    }
    @Test
    public void calling_pending_approvals_should_retrieve_all_pending_approvals() throws Exception{

        mockMvc.perform(get("/api/government/pending-approvals").with(getMockedGovernmentAuthorityCredentials()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()", greaterThan(1)));
    }

}