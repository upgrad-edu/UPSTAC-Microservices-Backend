package org.upgrad.upstac.testrequests.consultation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.consultation.models.CreateConsultationRequest;
import org.upgrad.upstac.testrequests.consultation.models.DoctorSuggestion;
import org.upgrad.upstac.testrequests.lab.models.TestStatus;
import org.upgrad.upstac.testrequests.models.RequestStatus;
import org.upgrad.upstac.testrequests.services.TestRequestQueryService;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.upgrad.upstac.testutils.TestData.*;



@SpringBootTest
@Slf4j
class ConsultationControllerIntegrationTest {


    @Autowired
    ConsultationController consultationController;


    @Autowired
    TestRequestQueryService testRequestQueryService;


    @Test
    @WithUserDetails(value = DOCTOR_WITH_COMPLETED_CONSULTATION)
    public void calling_assignForConsultation_with_valid_test_request_id_should_update_the_request_status(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_COMPLETED);

        TestRequest updatedTestRequest =  consultationController.assignForConsultation(testRequest.getRequestId());
        assertThat(updatedTestRequest.getRequestId(),equalTo(testRequest.getRequestId()));
        assertThat(updatedTestRequest.getStatus(),equalTo(RequestStatus.DIAGNOSIS_IN_PROCESS));
        assertNotNull(updatedTestRequest.getConsultation());

    }

    public TestRequest getTestRequestByStatus(RequestStatus status) {
        return testRequestQueryService.findBy(status).stream().findFirst().get();
    }

    @Test
    @WithUserDetails(value = DOCTOR_WITH_COMPLETED_CONSULTATION)
    public void calling_assignForConsultation_with_valid_test_request_id_should_throw_exception(){

        Long InvalidRequestId= -34L;


        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            consultationController.assignForConsultation(InvalidRequestId);
        });

        assertThat(exception.getMessage(), containsString("Invalid ID"));


    }

    @Test
    @WithUserDetails(value = DOCTOR_WITH_COMPLETED_CONSULTATION)
    public void calling_updateConsultation_with_valid_test_request_id_should_update_the_request_status_and_update_consultation_details(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);

        CreateConsultationRequest createConsultationRequest = getCreateConsultationRequest(testRequest);
        TestRequest updatedTestRequest =  consultationController.updateConsultation(testRequest.getRequestId(),createConsultationRequest);


        assertThat(updatedTestRequest.getRequestId(),equalTo(testRequest.getRequestId()));
        assertThat(updatedTestRequest.getStatus(),equalTo(RequestStatus.COMPLETED));
        assertThat(updatedTestRequest.getConsultation().getSuggestion(),equalTo(createConsultationRequest.getSuggestion()));



    }


    @Test
    @WithUserDetails(value = DOCTOR_WITH_COMPLETED_CONSULTATION)
    public void calling_updateConsultation_with_invalid_test_request_id_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);

        CreateConsultationRequest createConsultationRequest = getCreateConsultationRequest(testRequest);


        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            consultationController.updateConsultation(-98L,createConsultationRequest);

        });

        assertThat(exception.getMessage(), containsString("Invalid ID"));

    }

    @Test
    @WithUserDetails(value = DOCTOR_WITH_COMPLETED_CONSULTATION)
    public void calling_updateConsultation_with_invalid_empty_status_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);

        CreateConsultationRequest createConsultationRequest = getCreateConsultationRequest(testRequest);

        createConsultationRequest.setSuggestion(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            consultationController.updateConsultation(testRequest.getRequestId(),createConsultationRequest);

        });

        assertThat(exception.getMessage(), containsString("ConstraintViolationException"));

    }

    public CreateConsultationRequest getCreateConsultationRequest(TestRequest testRequest) {
        CreateConsultationRequest createConsultationRequest = new CreateConsultationRequest();
        if(testRequest.getLabResult().getResult().equals(TestStatus.POSITIVE)){
            createConsultationRequest.setComments("looks ok, suggest to take medicines at home");
            createConsultationRequest.setSuggestion(DoctorSuggestion.HOME_QUARANTINE);
        } else{
            createConsultationRequest.setComments("ok");
            createConsultationRequest.setSuggestion(DoctorSuggestion.NO_ISSUES);
        }
        return createConsultationRequest;
    }

}