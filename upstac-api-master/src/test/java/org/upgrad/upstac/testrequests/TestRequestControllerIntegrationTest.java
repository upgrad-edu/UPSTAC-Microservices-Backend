package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.testrequests.flow.TestRequestFlow;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.upgrad.upstac.testutils.TestData.*;

@SpringBootTest
@Slf4j
class TestRequestControllerIntegrationTest {


    @Autowired
    TestRequestController testRequestController;


    @Test
    @WithUserDetails(value = USER_WITH_COMPLETED_TEST)
    public void calling_getFlowById_with_valid_id_should_return_flows_for_all_test_requests() {

        List<TestRequestFlow> requests = testRequestController.getFlowByIdFor(REQUEST_ID_FOR_COMPLETED_TEST);
        assertThat(requests.size(), greaterThan(0));
        TestRequestFlow firstFlow = requests.get(0);
        assertNotNull(firstFlow.toString());

    }

    @Test
    @WithUserDetails(value = USER_WITH_COMPLETED_TEST)
    public void calling_getFlowById_with_invalid_id_should_throw_exception() {

        Long InvalidRequestId = -34L;


        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            testRequestController.getFlowByIdFor(InvalidRequestId);
        });

        assertThat(exception.getMessage(), containsString("Invalid ID"));


    }

    @Test
    @WithUserDetails(value = DOCTOR_WITH_COMPLETED_CONSULTATION)
    public void calling_getById_with_valid_id_should_return_test_request() {

        TestRequest request = testRequestController.getById(REQUEST_ID_FOR_COMPLETED_TEST);
        assertThat(request.getConsultation().getDoctor().getUserName(), equalTo(DOCTOR_WITH_COMPLETED_CONSULTATION));


    }

    @Test
    @WithUserDetails(value = UNAPPROVED_DOCTOR)
    public void calling_getById_with_valid_request_id_and_invalid_doctor_should_throw_bad_request() {

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            testRequestController.getById(REQUEST_ID_FOR_COMPLETED_TEST);
        });

        assertThat(exception.getMessage(), containsString("Invalid ID"));

    }
}