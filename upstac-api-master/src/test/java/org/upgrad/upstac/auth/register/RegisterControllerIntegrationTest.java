package org.upgrad.upstac.auth.register;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.models.AccountStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.upgrad.upstac.config.loaddata.DataGenerator.createRegisterRequestWithRandomPinCode;

@SpringBootTest
class RegisterControllerIntegrationTest {


    @Autowired
    RegisterController registerController;


    @Test
    public void calling_register_doctor_should_register_doctor_but_status_should_not_be_approved()  {


        final String nameOfDoctor = "somedoctor";
        RegisterRequest registerRequest = createRegisterRequestWithRandomPinCode(nameOfDoctor);

        User doctor = registerController.saveDoctor(registerRequest);

        assertThat(doctor.getUserName(), equalTo(nameOfDoctor));
        assertThat(doctor.getStatus(), equalTo(AccountStatus.INITIATED));

    }







}