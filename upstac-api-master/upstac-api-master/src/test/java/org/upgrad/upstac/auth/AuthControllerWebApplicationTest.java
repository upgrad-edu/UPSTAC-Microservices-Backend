package org.upgrad.upstac.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.upgrad.upstac.auth.register.RegisterRequest;
import org.upgrad.upstac.users.models.AccountStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.upgrad.upstac.config.loaddata.DataGenerator.createRegisterRequestWithRandomPinCode;
import static org.upgrad.upstac.testutils.JsonHelper.getAsJsonString;


//By default MockMVC disables spring security config files
@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
class AuthControllerWebApplicationTest {

    @Autowired
    MockMvc mockMvc;



    @Test
    public void calling_register_tester_should_register_tester_but_status_should_not_be_approved() throws Exception{


        final String nameOfTester = "someTester";
        RegisterRequest registerRequest = createRegisterRequestWithRandomPinCode(nameOfTester);



        mockMvc.perform(post("/auth/tester/register").contentType(APPLICATION_JSON_VALUE)
                .content(getAsJsonString(registerRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.userName", equalTo(nameOfTester.toLowerCase())))
                .andExpect(jsonPath("$.status", equalTo(AccountStatus.INITIATED.name())));


    }

}