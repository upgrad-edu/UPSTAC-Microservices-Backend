package org.upgrad.upstac.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.upgrad.upstac.auth.register.RegisterRequest;
import org.upgrad.upstac.users.roles.RoleService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.upgrad.upstac.config.loaddata.DataGenerator.createRegisterRequestWith;

//All Unit tests will use Core Junit Test

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @InjectMocks
    UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    RoleService roleService;
    @Mock

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Test
    public void whenUserDoesNotExistThenRegisteredUserShouldBeSaved() {

        RegisterRequest registerRequestWith = createRegisterRequestWith("testUser", 46346169);

        when(userRepository.save(any())).thenReturn(new User());

        userService.addDoctor(registerRequestWith);

        verify(userRepository).save(any());
        verify(bCryptPasswordEncoder, times(1)).encode(any());

    }

}