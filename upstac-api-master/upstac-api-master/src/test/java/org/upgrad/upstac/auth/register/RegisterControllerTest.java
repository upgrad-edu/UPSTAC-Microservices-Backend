package org.upgrad.upstac.auth.register;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.TokenProvider;
import org.upgrad.upstac.exception.AppException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.upgrad.upstac.config.loaddata.DataGenerator.createRegisterRequestWithRandomPinCode;


@ExtendWith(MockitoExtension.class)
@Slf4j
class RegisterControllerTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    TokenProvider tokenProvider;

    @Mock
    RegisterService registerService;

    @InjectMocks
    RegisterController registerController;

    @Mock
    Authentication authentication;

    @AfterEach
    public void onCOmplete(){

        Mockito.reset(authenticationManager);
    }


    @Test
    public void when_userservice_throws_error_when_calling_register_user_controller_should_throw_exception() {

        RegisterRequest registerRequest = createRegisterRequestWithRandomPinCode("someuser");
        log.info(registerRequest.toString());
        when(registerService.addUser(registerRequest)).thenThrow(new AppException("User with same name already exists") );

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            registerController.saveUser(registerRequest);
        });

        assertThat(exception.getStatus(), equalTo(HttpStatus.BAD_REQUEST));
        assertThat(exception.getMessage(), containsString("User with same name already exists"));
    }

    @Test
    public void when_userservice_throws_error_when_calling_register_doctor_controller_should_throw_exception() {

        RegisterRequest registerRequest = createRegisterRequestWithRandomPinCode("somedoctor");
        when(registerService.addDoctor(registerRequest)).thenThrow(new AppException("User with same name already exists") );

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            registerController.saveDoctor(registerRequest);
        });

        assertThat(exception.getStatus(), equalTo(HttpStatus.BAD_REQUEST));
        assertThat(exception.getMessage(), containsString("User with same name already exists"));
    }

    @Test
    public void when_userservice_throws_error_when_calling_register_tester_controller_should_throw_exception() {

        RegisterRequest registerRequest = createRegisterRequestWithRandomPinCode("someTester");
        when(registerService.addTester(registerRequest)).thenThrow(new AppException("User with same name already exists") );

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            registerController.saveTester(registerRequest);
        });

        assertThat(exception.getStatus(), equalTo(HttpStatus.BAD_REQUEST));
        assertThat(exception.getMessage(), containsString("User with same name already exists"));
    }
}