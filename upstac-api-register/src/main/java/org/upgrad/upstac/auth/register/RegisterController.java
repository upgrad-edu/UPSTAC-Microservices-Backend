package org.upgrad.upstac.auth.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;

@RestController
public class RegisterController {
	@Value("${rootURL}")
    private static  String rootURL;

    private RegisterService registerService;

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public RegisterController( RegisterService userService) {
        this.registerService = userService;
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public RegisterRequest saveUser(@RequestBody RegisterRequest user) throws JsonProcessingException {
            String userName = user.getUserName();
            String email = user.getEmail();
            String phone = user.getPhoneNumber();
            if (!registerService.validateUser(userName,email,phone)) {
               RegisterRequest newUser = registerService.addUser(user);
               return newUser;
            } else {
                throw asBadRequest("User already exists");
            }

    }

    @RequestMapping(value = "/auth/doctor/register", method = RequestMethod.POST)
    public RegisterRequest saveDoctor(@RequestBody RegisterRequest user) {
        String userName = user.getUserName();
        String email = user.getEmail();
        String phone = user.getPhoneNumber();
        if (!registerService.validateUser(userName,email,phone)) {
            RegisterRequest newDoctor = registerService.addDoctor(user);
            return newDoctor;
        } else {
            throw asBadRequest("Doctor already exists");
        }
    }


    @RequestMapping(value = "/auth/tester/register", method = RequestMethod.POST)
    public RegisterRequest saveTester(@RequestBody RegisterRequest user) {
        String userName = user.getUserName();
        String email = user.getEmail();
        String phone = user.getPhoneNumber();
        if (!registerService.validateUser(userName,email,phone)) {
            RegisterRequest newtester = registerService.addTester(user);
            return newtester;
        } else {
            throw asBadRequest("Tester already exists");
        }
    }
/*
    private static boolean validateUser(String userName , String email, String phone)
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
    
    */
}
