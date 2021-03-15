package org.upgrad.upstac.documents;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.UserService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.upgrad.upstac.testutils.TestDataGenerator.getMockedMultipartFile;

@SpringBootTest
@Slf4j
class DocumentServiceIntegrationTest {


    @Autowired
    DocumentService documentService;

    @Test
    public void test_pendingApprovals(){


        List<Document> documents= documentService.getPendingApprovals();
        assertThat(documents.size(),greaterThan(0));

    }



}