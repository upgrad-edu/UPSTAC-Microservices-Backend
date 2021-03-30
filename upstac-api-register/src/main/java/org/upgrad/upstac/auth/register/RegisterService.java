package org.upgrad.upstac.auth.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RegisterService {

    @Value("${rootURL}")
    private String rootURL;

    private static final Logger log = LoggerFactory.getLogger(RegisterService.class);


    public RegisterRequest addUser(RegisterRequest user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequest> request = new HttpEntity<>(user, headers);
        RestTemplate restTemplate = new RestTemplate();
        String createUser = rootURL + "/auth/newuser";
        RegisterRequest newUser = restTemplate.postForObject(createUser, request, RegisterRequest.class);
        return newUser;
    }

    public RegisterRequest addDoctor(RegisterRequest user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequest> request = new HttpEntity<>(user, headers);
        RestTemplate restTemplate = new RestTemplate();
        String createUser = rootURL + "/auth/newdoctor";
        RegisterRequest newDoctor = restTemplate.postForObject(createUser, request, RegisterRequest.class);
        return newDoctor;
    }

    public RegisterRequest addTester(RegisterRequest user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequest> request = new HttpEntity<>(user, headers);
        RestTemplate restTemplate = new RestTemplate();
        String createUser = rootURL +"/auth/newtester";
        RegisterRequest newtester = restTemplate.postForObject(createUser, request, RegisterRequest.class);
        return newtester;
    }
    public  boolean validateUser(String userName , String email, String phone)
    {
    	//String rootURL="http://localhost:8080";
    	System.out.println("rootURL"+rootURL);
        String uri = rootURL + "/auth/validateusername/"
                + userName + "/"
                + email + "/"
                + phone;
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("printing uri"+uri);
        Boolean result = restTemplate.getForObject(uri, Boolean.class);
        System.out.println(result);
        return result;
    }

    /*public User addGovernmentAuthority(RegisterRequest user) {

        if((null != userService.findByUserName(user.getUserName())))
            throw new AppException("Username already exists " + user.getUserName());

        if((null != userService.findByEmail(user.getEmail())))
            throw new AppException("User with Same email already exists " + user.getEmail());


        if((null != userService.findByPhoneNumber(user.getPhoneNumber())))
            throw new AppException("User with Same Phone number already exists " + user.getPhoneNumber());


        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setPassword(userService.toEncrypted(user.getPassword()));
        newUser.setRoles(userService.getRoleFor(UserRole.GOVERNMENT_AUTHORITY));
        newUser.setCreated(LocalDateTime.now());
        newUser.setUpdated(LocalDateTime.now());
        newUser.setAddress(user.getAddress());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setPinCode(user.getPinCode());
        newUser.setGender(user.getGender());
        newUser.setAddress(user.getAddress());
        newUser.setDateOfBirth(getDateFromString(user.getDateOfBirth()));
        newUser.setStatus(AccountStatus.APPROVED);
        User updatedUser = userService.saveInDatabase(newUser);


        return updatedUser;


    }*/


}
