package org.upgrad.upstac.testrequests.authority;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.documents.Document;
import org.upgrad.upstac.documents.DocumentService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.authority.models.UpdateApprovalRequest;
import org.upgrad.upstac.testrequests.services.TestRequestQueryService;
import org.upgrad.upstac.settings.TestPositiveCountThreshold;
import org.upgrad.upstac.settings.ThresholdService;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.UserService;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;
import static org.upgrad.upstac.exception.UpgradResponseStatusException.asConstraintViolation;


@RestController
@RequestMapping("/api/government")
public class GovernmentAuthorityController {

    Logger log = LoggerFactory.getLogger(GovernmentAuthorityController.class);


    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserService userService;


    @Autowired
    private ThresholdService thresholdService;


    @Autowired
    private TestRequestQueryService testRequestQueryService;


    @Autowired
    private UserLoggedInService userLoggedInService;


    @GetMapping("/all-requests")
    @PreAuthorize("hasAnyRole('GOVERNMENT_AUTHORITY')")
    public List<TestRequest> getAll() {

        return testRequestQueryService.findAll();

    }


    @GetMapping("/pending-approvals")
    @PreAuthorize("hasAnyRole('GOVERNMENT_AUTHORITY')")
    public List<Document> getAllPendingApprovals() {
        List<Document> documentSet = getPendingApprovals();
        return documentSet;
    }

    @PreAuthorize("hasAnyRole('GOVERNMENT_AUTHORITY')")
    @PutMapping("/update-approval")
    public User updateUserStatus(@RequestBody UpdateApprovalRequest updateApprovalRequest) {

        try {

            return  userService.updateApprovalStatus(updateApprovalRequest);
        } catch (ConstraintViolationException e) {
            throw asConstraintViolation(e);
        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }


    @PostMapping("/update-threshold")
    @PreAuthorize("hasAnyRole('GOVERNMENT_AUTHORITY')")
    public TestPositiveCountThreshold updateThreshold(@RequestBody TestPositiveCountThreshold testPositiveCountThreshold) {

            return thresholdService.update(testPositiveCountThreshold);

    }

    @PostMapping("/update-thresholds")
    @PreAuthorize("hasAnyRole('GOVERNMENT_AUTHORITY')")
    public List<TestPositiveCountThreshold> updateThresholds(@RequestBody List<TestPositiveCountThreshold> testPositiveCountThresholds) {

            return thresholdService.updateAll(testPositiveCountThresholds);

    }

    @GetMapping("/all-thresholds")
    @PreAuthorize("hasAnyRole('GOVERNMENT_AUTHORITY')")
    public List<TestPositiveCountThreshold> allThresholds() {
            return thresholdService.findAll();
    }

    private static List<Document> getPendingApprovals(){
        String uri = "http://localhost:8082/documents/getPendingApprovals";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity result = restTemplate.getForObject(uri, ResponseEntity.class);
        System.out.println(result);
        return (List)result.getBody();
    }

}
