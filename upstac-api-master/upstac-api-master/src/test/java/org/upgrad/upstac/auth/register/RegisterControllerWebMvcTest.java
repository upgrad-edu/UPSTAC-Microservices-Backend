package org.upgrad.upstac.auth.register;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.upgrad.upstac.auth.AuthController;
import org.upgrad.upstac.auth.register.RegisterRequest;
import org.upgrad.upstac.config.security.TokenProvider;
import org.upgrad.upstac.config.security.UnAuthorizedHandler;
import org.upgrad.upstac.users.UserService;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.upgrad.upstac.config.loaddata.DataGenerator.createRegisterRequestWithRandomPinCode;
import static org.upgrad.upstac.testutils.JsonHelper.getAsJsonString;
import static org.upgrad.upstac.testutils.TestDataGenerator.createMockUser;

@WebMvcTest(controllers = RegisterController.class)
@ExtendWith(SpringExtension.class)
class RegisterControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    UserService userService;

    @MockBean
    TokenProvider tokenProvider;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    @Qualifier("UpgradUserDetailsService")
    UserDetailsService UpgradUserDetailsService;

    @MockBean
    UnAuthorizedHandler unauthorizedHandler;


    @MockBean
    RegisterService registerService;


    @Test
    public void calling_register_user_with_valid_credentials_should_register_user() throws Exception{



      RegisterRequest registerRequest= createRegisterRequestWithRandomPinCode("someuser");

        when(registerService.addUser(registerRequest)).thenReturn(createMockUser(registerRequest));


        mockMvc.perform(post("/auth/register").contentType(APPLICATION_JSON_VALUE)
                .content(getAsJsonString(registerRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.userName", equalTo("someuser")));


    }

}