package org.upgrad.upstac.settings;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.upgrad.upstac.documents.DocumentService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
class ThresholdServiceIntegrationTest {

    @SpyBean
    ThresholdService thresholdService;

    @Test
    public void findAllShouldReturnAllThresholdDetails(){


                assertThat(thresholdService.findAll().size(),equalTo(3));
    }

    @Test
    public void updateAllShouldUpdateAllThresholdDetails(){

        List<TestPositiveCountThreshold> requests = new ArrayList<>();
        requests.add(createTestPositiveCountThreshold(ThresholdType.RED, 5));
        requests.add(createTestPositiveCountThreshold(ThresholdType.YELLOW, 3));
        requests.add(createTestPositiveCountThreshold(ThresholdType.GREEN, 1));
        final List<TestPositiveCountThreshold> responses = thresholdService.updateAll(requests);

        assertThat(responses.size(),equalTo(3));
    }

    @Test
    public void updateShouldUpdateThresholdDetails(){

        TestPositiveCountThreshold red=   createTestPositiveCountThreshold(ThresholdType.RED, 5);

        TestPositiveCountThreshold response = thresholdService.update(red);

        assertThat(response.getMaxLimit(),equalTo(red.getMaxLimit()));
    }

    public TestPositiveCountThreshold createTestPositiveCountThreshold(ThresholdType thresholdType, int maxLimit) {
        final TestPositiveCountThreshold e = new TestPositiveCountThreshold();
        e.setMaxLimit(maxLimit);
        e.setThresholdType(thresholdType);
        return e;
    }


}