package org.upgrad.upstac.testrequests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.testrequests.models.CreateTestRequest;
import org.upgrad.upstac.testrequests.models.RequestStatus;
import org.upgrad.upstac.users.UserService;
import org.upgrad.upstac.users.models.Gender;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.upgrad.upstac.testutils.JsonHelper.getAsJsonString;
import static org.upgrad.upstac.testutils.TestData.getMockedUserCredentials;

@SpringBootTest
@AutoConfigureMockMvc
class TestRequestControllerWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    UserService userService;


    @SpyBean
    private UserLoggedInService userLoggedInService;



    @Test
    public void calling_register_testrequest_should_create_test_request() throws Exception{


        final String user = "someone";
        CreateTestRequest createTestRequest = createTestRequestWith(user, "123456789");
        makeTestRequestAndValidate(createTestRequest, "someone");
        Mockito.verify(userLoggedInService).getLoggedInUser();
    }

    public void makeTestRequestAndValidate(CreateTestRequest createTestRequest, String someone) throws Exception {
        mockMvc.perform(post("/api/testrequests").with(getMockedUserCredentials()).contentType(APPLICATION_JSON_VALUE)
                .content(getAsJsonString(createTestRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", equalTo(someone)))
                .andExpect(jsonPath("$.status", equalTo(RequestStatus.INITIATED.name())));
    }

    public CreateTestRequest createTestRequestWith(String user, String phoneNumber) {
        CreateTestRequest createTestRequest = new CreateTestRequest();
        createTestRequest.setAddress("some Addres");
        createTestRequest.setAge(98);
        createTestRequest.setEmail("someone" + phoneNumber + "@somedomain.com");
        createTestRequest.setGender(Gender.MALE);
        createTestRequest.setName(user);
        createTestRequest.setPhoneNumber(phoneNumber);
        createTestRequest.setPinCode(716768);
        return createTestRequest;
    }

    @Test
    public void calling_register_testrequest_With_same_phonenumber_should_throw_badrequest() throws Exception{


        final String user = "someone";


        CreateTestRequest createTestRequest = createTestRequestWith(user, "223456789");


        makeTestRequestAndValidate(createTestRequest, user);


        mockMvc.perform(post("/api/testrequests").with(getMockedUserCredentials()).contentType(APPLICATION_JSON_VALUE)
                .content(getAsJsonString(createTestRequest)))
                .andExpect(status().isBadRequest());



    }
    @Test
    public void calling_register_testrequest_without_phonenumber_should_throw_badrequest() throws Exception{


        CreateTestRequest createTestRequest = createTestRequestWith("someone", null);




        mockMvc.perform(post("/api/testrequests").with(getMockedUserCredentials()).contentType(APPLICATION_JSON_VALUE)
                .content(getAsJsonString(createTestRequest)))
                .andExpect(status().isBadRequest());



    }

    @Test
    public void calling_register_testrequest_without_valid_credentials_should_throw_unauthorized() throws Exception{

        CreateTestRequest createTestRequest = createTestRequestWith("someone", "some");
        mockMvc.perform(post("/api/testrequests").contentType(APPLICATION_JSON_VALUE)
                .content(getAsJsonString(createTestRequest)))
                .andExpect(status().isUnauthorized());



    }

    @Test
    public void calling_get_requests_should_retrieve_all_the_testrequests() throws Exception{

        final String user = "someone";
        CreateTestRequest createTestRequest = createTestRequestWith(user, "323456789");
        makeTestRequestAndValidate(createTestRequest, user);

        mockMvc.perform(get("/api/testrequests").with(getMockedUserCredentials()))
                .andExpect(status().isOk())
                .andDo(print());



    }

}