package org.upgrad.upstac.users;


import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.upgrad.upstac.users.models.UpdateUserDetailRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest

class UserControllerIntegrationTest {


    @Autowired
    UserController userController;


    private static final Logger log = LoggerFactory.getLogger(UserControllerIntegrationTest.class);

    @Test
    @WithUserDetails(value = "authority")
    void WhenCallingListUsersWithRoleAdminTheSizeShouldBeGreaterThanZero() {

        assertThat(userController.listUsers().size(), greaterThan(0));
    }

    @Test
    @WithUserDetails(value = "authority")
    void WhenCallingupdateUserDetailsExpectDetailsToBeUpdated() {

        UpdateUserDetailRequest updateUserDetailRequest = new UpdateUserDetailRequest();
        updateUserDetailRequest.setFirstName("myAuthority");
       User user = userController.updateUserDetails(updateUserDetailRequest);
        assertThat(user.getFirstName(), equalTo("myAuthority"));
    }

    @Test
    @WithUserDetails(value = "user")
    void WhenCallingListUsersWithRoleUserThenItShouldThrowAccessDeniedException() {

        assertThrows(AccessDeniedException.class, () -> {
            userController.listUsers();
        });

    }

    @Test
    @WithUserDetails(value = "user")
    void WhengetMyDetailsMethodWithRoleUserThenItShouldReturnTheLoggedInUserDetails() {
        User user = userController.getMyDetails();
        assertThat(user.getUserName(), equalTo("user"));
    }

    @Test
    @WithAnonymousUser
    void WhengetMyDetailsMethodWithInvalidUserDetailsThenItShouldThrowAccessDeniedException() {

        assertThrows(AccessDeniedException.class, () -> {
            userController.getMyDetails();
        });

    }


}