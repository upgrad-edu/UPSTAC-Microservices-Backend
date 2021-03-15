package org.upgrad.upstac.users;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.ForbiddenException;
import org.upgrad.upstac.users.credentials.ChangePasswordRequest;
import org.upgrad.upstac.users.credentials.ChangePasswordService;
import org.upgrad.upstac.users.models.AccountStatus;
import org.upgrad.upstac.users.models.UpdateUserDetailRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asConstraintViolation;
import static org.upgrad.upstac.exception.UpgradResponseStatusException.asForbidden;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    UserLoggedInService userLoggedInService;


    @Autowired
    ChangePasswordService changePasswordService;


    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @PreAuthorize("hasRole('GOVERNMENT_AUTHORITY')")
    @GetMapping
    public List<User> listUsers() {

        return userService.findAll();
    }


    @PreAuthorize("hasAnyRole('USER','GOVERNMENT_AUTHORITY','TESTER','DOCTOR')")
    @GetMapping(value = "/details")
    public User getMyDetails() {

        return userLoggedInService.getLoggedInUser();
    }


    @PreAuthorize("hasAnyRole('USER','GOVERNMENT_AUTHORITY','TESTER','DOCTOR')")
    @PutMapping(value = "/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {


        try {
            log.info("No errors to change based on" + changePasswordRequest.getPassword());

            User user = userLoggedInService.getLoggedInUser();
            changePasswordService.changePassword(user, changePasswordRequest);
            return ResponseEntity.ok("Succesfully Changed");

        } catch (ConstraintViolationException e) {
            throw asConstraintViolation(e);
        }catch (ForbiddenException e) {
            throw asForbidden(e.getMessage());
        }


    }

    @PreAuthorize("hasAnyRole('USER','GOVERNMENT_AUTHORITY','TESTER','DOCTOR')")
    @DeleteMapping(value = "/closeaccount")
    public ResponseEntity<?> closeAccount() {

        User user = userLoggedInService.getLoggedInUser();
        deleteUserByName(user.getUserName());


        return ResponseEntity.ok("Succesfully Closed Account");
    }


    @PreAuthorize("hasAnyRole('GOVERNMENT_AUTHORITY')")
    @DeleteMapping(value = "/deleteuser/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {


        deleteUserByName(username);


        return ResponseEntity.ok("Succesfully removed User");
    }

    public void deleteUserByName(@PathVariable String username) {
        User user = userService.findByUserName(username);
        userService.updateStatusAndSave(user, AccountStatus.DELETED);
    }


    @PreAuthorize("hasAnyRole('USER','GOVERNMENT_AUTHORITY','TESTER','DOCTOR')")
    @PutMapping
    public User updateUserDetails(@RequestBody UpdateUserDetailRequest updateUserDetailRequest) {
        try {
            User user = userLoggedInService.getLoggedInUser();
            return userService.updateUserDetails(user,updateUserDetailRequest);
        } catch (ConstraintViolationException e) {
            throw asConstraintViolation(e);
        }


    }


}
