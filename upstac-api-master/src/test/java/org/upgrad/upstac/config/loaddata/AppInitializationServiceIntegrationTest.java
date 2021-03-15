package org.upgrad.upstac.config.loaddata;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.upgrad.upstac.users.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
@Slf4j
@ActiveProfiles("noload")
class AppInitializationServiceIntegrationTest {



    @Autowired
    AppInitializationService appInitializationService;

    @SpyBean
    UserService userService;

//    @Test
//
//    @Timeout(1240)
//    public void when_initialize_called_expect_all_services_being_called(){
//
//        appInitializationService.initialize();
//        Mockito.verify(userService,atLeastOnce()).addDoctor(any());
//        Mockito.verify(userService,atLeastOnce()).addUser(any());
//        Mockito.verify(userService,atLeastOnce()).addTester(any());
//    }
}