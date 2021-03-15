package org.upgrad.upstac.users;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.upgrad.upstac.config.security.UserLoggedInService;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.upgrad.upstac.testutils.TestData.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    UserService userService;




    @Test
    public void calling_closeAccount_should_close_the_account() throws Exception{
      //  chrissiebritcher
        //        burtiemunnis
        mockMvc.perform(delete("/users/closeaccount").with(getMockedCloseAccountCredentials()))
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    public void calling_deleteUser_should_delete_the_account() throws Exception{

        mockMvc.perform(delete("/users/deleteuser/burtiemunnis").with(getMockedGovernmentAuthorityCredentials()))
                .andExpect(status().isOk())
                .andDo(print());

    }


}