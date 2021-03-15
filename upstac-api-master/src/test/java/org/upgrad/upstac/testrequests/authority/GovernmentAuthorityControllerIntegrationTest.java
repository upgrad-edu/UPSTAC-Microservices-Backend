package org.upgrad.upstac.testrequests.authority;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.settings.TestPositiveCountThreshold;
import org.upgrad.upstac.settings.ThresholdType;
import org.upgrad.upstac.testrequests.authority.models.UpdateApprovalRequest;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.UserService;
import org.upgrad.upstac.users.models.AccountStatus;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.upgrad.upstac.testutils.TestData.*;

@SpringBootTest
@Slf4j
class GovernmentAuthorityControllerIntegrationTest {


    @Autowired
    GovernmentAuthorityController governmentAuthorityController;


    @Autowired
    UserService userService;


    @Test
    @WithUserDetails(value = DEFAULT_AUTHORITY)
    public void updateUserApprovals_with_invalid_id_should_throw_exception() {

        UpdateApprovalRequest updateApprovalRequest =new UpdateApprovalRequest();
        updateApprovalRequest.setUserId(-2L);
        updateApprovalRequest.setStatus(AccountStatus.APPROVED);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{

            governmentAuthorityController.updateUserStatus(updateApprovalRequest);
        });


        assertThat(exception.getMessage(), containsString("Invalid User ID"));

    }

    @Test
    @WithUserDetails(value = DEFAULT_AUTHORITY)
    public void updateUserApprovals_with_empty_status_should_throw_exception() {

        UpdateApprovalRequest updateApprovalRequest =new UpdateApprovalRequest();
        updateApprovalRequest.setUserId(UNAPPROVED_DOCTOR_ID);
        updateApprovalRequest.setStatus(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{

            governmentAuthorityController.updateUserStatus(updateApprovalRequest);
        });


        assertThat(exception.getStatus(),equalTo(HttpStatus.BAD_REQUEST));

    }



    @Test
    @WithUserDetails(value = DEFAULT_AUTHORITY)
    public void updateUserApprovals_should_update_approval_status() {

        UpdateApprovalRequest updateApprovalRequest =new UpdateApprovalRequest();
        updateApprovalRequest.setUserId(UNAPPROVED_DOCTOR_ID);
        updateApprovalRequest.setStatus(AccountStatus.APPROVED);

        User updatedUser = governmentAuthorityController.updateUserStatus(updateApprovalRequest);

        assertThat(updatedUser.getStatus(), equalTo(AccountStatus.APPROVED));

    }
    @Test
    @WithUserDetails(value = DEFAULT_AUTHORITY)
    public void updateThreshold_should_update_threshold_settings() {


        List<TestPositiveCountThreshold> allThresholds = governmentAuthorityController.allThresholds();

        assertThat(allThresholds.size(), equalTo(3));


        TestPositiveCountThreshold redThreshold = new TestPositiveCountThreshold();
        redThreshold.setThresholdType(ThresholdType.RED);
        redThreshold.setMaxLimit(25);

        TestPositiveCountThreshold updatedRedThreshold = governmentAuthorityController.updateThreshold(redThreshold);

        assertThat(updatedRedThreshold, equalTo(redThreshold));

        List<TestPositiveCountThreshold> thresholdsToBeUpdated = allThresholds
                .stream()
                .map(updateThresholdMaxByFive())
                .collect(Collectors.toList());


        List<TestPositiveCountThreshold> results = governmentAuthorityController.updateThresholds(thresholdsToBeUpdated);

        assertThat(results, equalTo(thresholdsToBeUpdated));

    }

    public Function<TestPositiveCountThreshold, TestPositiveCountThreshold> updateThresholdMaxByFive() {
        return testPositiveCountThreshold -> {
            testPositiveCountThreshold.setMaxLimit(testPositiveCountThreshold.getMaxLimit() + 5);
            return testPositiveCountThreshold;
        };
    }


}