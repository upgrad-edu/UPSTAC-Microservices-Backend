package org.upgrad.upstac.auth.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.users.User;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;

@RestController
public class RegisterController {



    private RegisterService registerService;


    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);


    @Autowired
    public RegisterController( RegisterService userService) {

        this.registerService = userService;
    }


    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public User saveUser(@RequestBody RegisterRequest user) {

        try {
            return registerService.addUser(user);
        } catch (AppException e) {
            throw   asBadRequest(e.getMessage());
        }


    }


    @RequestMapping(value = "/auth/doctor/register", method = RequestMethod.POST)
    public User saveDoctor(@RequestBody RegisterRequest user) {

        try {
            return registerService.addDoctor(user);
        } catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }


    @RequestMapping(value = "/auth/tester/register", method = RequestMethod.POST)
    public User saveTester(@RequestBody RegisterRequest user) {

        try {
            return registerService.addTester(user);
        } catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }
}
