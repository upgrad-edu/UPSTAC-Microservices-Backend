package org.upgrad.upstac.auth;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.auth.models.LoginRequest;
import org.upgrad.upstac.auth.models.LoginResponse;
import org.upgrad.upstac.auth.register.RegisterRequest;
import org.upgrad.upstac.config.security.TokenProvider;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.users.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.upgrad.upstac.config.loaddata.DataGenerator.createRegisterRequestWithRandomPinCode;
import static org.upgrad.upstac.testutils.TestDataGenerator.createLoginRequestWIth;


@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthControllerTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    TokenProvider tokenProvider;

    @Mock
    UserService userService;

    @InjectMocks
    AuthController authController;

    @Mock
    Authentication authentication;

    @AfterEach
    public void onCOmplete(){

        Mockito.reset(authenticationManager);
    }

    @Test
    public void calling_login_with_valid_credentials_should_return_response() {
        LoginRequest loginRequest = createLoginRequestWIth("validuser", "validpassword");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());




        String someValidToken = "ARKWDer$%#$";
        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authentication);
        when(userService.isApprovedUser(loginRequest.getUserName())).thenReturn(true);
        when(tokenProvider.generateToken(authentication)).thenReturn(someValidToken);

        ResponseEntity<?> response = authController.login(loginRequest);

        LoginResponse loginResponse = (LoginResponse) response.getBody();
        log.info(loginResponse.toString());
        assertThat(loginResponse.getUserName(), equalTo(loginRequest.getUserName()));
        assertThat(loginResponse.getToken(), equalTo(someValidToken));
    }

    @Test
    public void calling_login_with_valid_credentials_but_not_approved_user_should_throw_exception() {
        LoginRequest loginRequest = createLoginRequestWIth("notApproveduser", "validpassword");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());


        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authentication);
        when(userService.isApprovedUser(loginRequest.getUserName())).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            authController.login(loginRequest);
        });

        assertThat(exception.getMessage(), containsString("User Not Approved"));
    }
    @Test
    public void calling_login_with_invalid_credentials_should_throw_exception() {
        LoginRequest loginRequest = createLoginRequestWIth("user", "invalidpassword");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());


        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenThrow(new BadCredentialsException("Invalid Credentials") );

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            authController.login(loginRequest);
        });

        assertThat(exception.getMessage(), containsString("Invalid Credentials"));
    }




}