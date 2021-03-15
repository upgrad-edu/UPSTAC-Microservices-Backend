package org.upgrad.upstac.users;


import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.upgrad.upstac.exception.UpgradResponseStatusException;
import org.upgrad.upstac.users.credentials.ChangePasswordRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class ChangePasswordIntegrationTest {


    @Autowired
    UserController userController;


    private static final Logger log = LoggerFactory.getLogger(ChangePasswordIntegrationTest.class);



    @Test
    @WithUserDetails(value = "user")
    void WhenChangePasswordMethodCalledWithPasswordThenItShouldChangeThePassword() {
        ResponseEntity<?> responseEntity = changePassword("password", "new-password");

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        //Revert Back
        responseEntity = changePassword( "new-password","password");
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));



    }

    @Test
    @WithUserDetails(value = "user")
    void WhenChangePasswordMethodCalledWithInvalidOldPasswordThenItShouldThrowError() {


        UpgradResponseStatusException exception= assertThrows(UpgradResponseStatusException.class,()->{
            changePassword("invalid-password","somedummypassword");
        });


        assertThat(exception.getMessage(),containsString("Bad credentials"));


    }

    @Test
    @WithUserDetails(value = "user")
    void WhenChangePasswordMethodCalledWithInvalidParametersThenItShouldThrowError() {


        RuntimeException exception= assertThrows(RuntimeException.class,()->{
            changePassword("password","some");
        });


        assertThat(exception.getMessage(),containsString("ConstraintViolationException"));
        assertThat(exception.getMessage(),containsString("size must be between 8 and"));


    }

    private ResponseEntity<?> changePassword(String oldPassword, String newPassword) {
        ChangePasswordRequest changePasswordRequest= new ChangePasswordRequest();

        changePasswordRequest.setOldPassword(oldPassword);

        changePasswordRequest.setPassword(newPassword);

        return userController.changePassword(changePasswordRequest);
    }

}