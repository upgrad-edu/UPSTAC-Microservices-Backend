package org.upgrad.upstac.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.UserService;


@Component
public class UserLoggedInService {

    private UserService userService;

    @Autowired
    public UserLoggedInService(UserService userService) {
        this.userService = userService;
    }


    public User getLoggedInUser() {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.findByUserName(principal.getUsername());

    }


}
