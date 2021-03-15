package org.upgrad.upstac.testutils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.upgrad.upstac.auth.models.LoginRequest;
import org.upgrad.upstac.auth.register.RegisterRequest;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.models.AccountStatus;
import org.upgrad.upstac.users.roles.Role;
import org.upgrad.upstac.users.roles.UserRole;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.upgrad.upstac.shared.DateParser.getDateFromString;
import static org.upgrad.upstac.shared.FileReader.getMultipartFileFrom;

@Slf4j
public class TestDataGenerator {



    static Long getRandomId() {
        Random r = new Random();
        int low = 1000;
        int high = 100000;
       int res= r.nextInt(high - low) + low;
       return Long.valueOf(res);

    }

 public static Role getFor(UserRole userRole) {
        Role role = new Role();
        role.setName(userRole.name());
        role.setId(getRandomId());
        return role;
    }

    public static Role getForUser() {

        return getFor(UserRole.USER);
    }


    public static Role getForDoctor() {

        return getFor(UserRole.DOCTOR);
    }


    public static Role getForAuthority() {

        return getFor(UserRole.GOVERNMENT_AUTHORITY);
    }
    public static MultipartFile getMockedMultipartFile() {
        return getMultipartFileFrom("test-id-2.png");
    }

    public  static User createMockUser(RegisterRequest registerRequest ){

        return createMockUserFromRegisterRequest(registerRequest, getForUser(), AccountStatus.APPROVED);
    }
    public static  User createMockUserFromRegisterRequest(RegisterRequest registerRequest, Role role, AccountStatus status ){
        User newUser = new User();
        newUser.setUserName(registerRequest.getUserName());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setRoles(getRolesForUser(role));
        newUser.setCreated(LocalDateTime.now());
        newUser.setUpdated(LocalDateTime.now());
        newUser.setAddress(registerRequest.getAddress());
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPhoneNumber(registerRequest.getPhoneNumber());
        newUser.setPinCode(registerRequest.getPinCode());
        newUser.setGender(registerRequest.getGender());
        newUser.setAddress(registerRequest.getAddress());
        newUser.setDateOfBirth(getDateFromString(registerRequest.getDateOfBirth()));
        newUser.setStatus(status);
        newUser.setId(getRandomId());

        return newUser;
    }

    static Set<Role> getRolesForUser(Role role) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }



    public static LoginRequest createLoginRequestWIth(String user, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(user);
        loginRequest.setPassword(password);
        return loginRequest;
    }
}
