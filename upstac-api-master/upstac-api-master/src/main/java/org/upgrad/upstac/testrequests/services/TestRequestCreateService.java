package org.upgrad.upstac.testrequests.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.TestRequestRepository;
import org.upgrad.upstac.testrequests.models.CreateTestRequest;
import org.upgrad.upstac.testrequests.models.RequestStatus;
import org.upgrad.upstac.users.User;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
@Validated
public class TestRequestCreateService {

    @Autowired
    private TestRequestRepository testRequestRepository;



    private static Logger logger = LoggerFactory.getLogger(TestRequestCreateService.class);



    public TestRequest createTestRequestFrom(User user,@Valid CreateTestRequest createTestRequest) {

        validateExistingRequestsNotPresentWithSameDetails(createTestRequest);

        TestRequest testRequest = new TestRequest();

        testRequest.setName(createTestRequest.getName());
        testRequest.setCreated(LocalDate.now());
        testRequest.setStatus(RequestStatus.INITIATED);
        testRequest.setAge(createTestRequest.getAge());
        testRequest.setEmail(createTestRequest.getEmail());
        testRequest.setPhoneNumber(createTestRequest.getPhoneNumber());
        testRequest.setPinCode(createTestRequest.getPinCode());
        testRequest.setAddress(createTestRequest.getAddress());
        testRequest.setGender(createTestRequest.getGender());

        testRequest.setCreatedBy(user);


        return saveTestRequest(testRequest);
    }

    public void validateExistingRequestsNotPresentWithSameDetails(CreateTestRequest createTestRequest) {
        List<TestRequest> testRequests= testRequestRepository.findByEmail(createTestRequest.getEmail());
        testRequests.addAll(testRequestRepository.findByPhoneNumber(createTestRequest.getPhoneNumber()));

        testRequests.stream()
                   .filter(testRequest -> testRequest.getStatus().equals(RequestStatus.COMPLETED) == false)
                   .findFirst()
                   .ifPresent(testRequest ->{
                           throw new AppException("A Request with same PhoneNumber or Email is already in progress ");
                   });
    }

    @Transactional
    public TestRequest saveTestRequest(@Valid TestRequest result) {


        return testRequestRepository.save(result);
    }





}
