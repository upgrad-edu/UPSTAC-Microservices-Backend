package org.upgrad.upstac.testrequests;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.flow.TestRequestFlow;
import org.upgrad.upstac.testrequests.flow.TestRequestFlowService;
import org.upgrad.upstac.testrequests.models.CreateTestRequest;
import org.upgrad.upstac.testrequests.services.TestRequestCreateService;
import org.upgrad.upstac.testrequests.services.TestRequestQueryService;
import org.upgrad.upstac.users.User;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;
import static org.upgrad.upstac.exception.UpgradResponseStatusException.asConstraintViolation;


@RestController
@RequestMapping("/api/testrequests")
public class TestRequestController {

    Logger log = LoggerFactory.getLogger(TestRequestController.class);


    @Autowired
    private TestRequestCreateService testRequestCreateService;


    @Autowired
    private TestRequestFlowService testRequestFlowService;

    @Autowired
    private TestRequestQueryService testRequestQueryService;


    @Autowired
    private UserLoggedInService userLoggedInService;


    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    public TestRequest createRequest(@RequestBody CreateTestRequest testRequest) {
        try {
            User user = userLoggedInService.getLoggedInUser();
            TestRequest result = testRequestCreateService.createTestRequestFrom(user, testRequest);
            return result;
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            throw asConstraintViolation(e);
        } catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }

    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public List<TestRequest> myRequests() {

        User user = userLoggedInService.getLoggedInUser();
        return testRequestQueryService.findByUser(user);


    }


    @GetMapping("/flow/{id}")
    @PreAuthorize("hasAnyRole('USER','GOVERNMENT_AUTHORITY','TESTER','DOCTOR')")
    public List<TestRequestFlow> getFlowByIdFor(@PathVariable Long id) {
        try {
            User user = userLoggedInService.getLoggedInUser();
            TestRequest testRequest = testRequestQueryService.findTestRequestForUserByID(user, id).orElseThrow(() -> new AppException("Invalid ID"));
            return testRequestFlowService.findByRequest(testRequest);
        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }

    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','GOVERNMENT_AUTHORITY','TESTER','DOCTOR')")
    public TestRequest getById(@PathVariable Long id) {
        try {
            User user = userLoggedInService.getLoggedInUser();
            TestRequest testRequest = testRequestQueryService.findTestRequestForUserByID(user, id).orElseThrow(() -> new AppException("Invalid ID"));
           return testRequest;
        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }

    }


}
