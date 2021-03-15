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

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.upgrad.upstac.testutils.TestDataGenerator.getMockedMultipartFile;

@SpringBootTest
@Slf4j
class DocumentControllerIntegrationTest {


    @Autowired
    DocumentController documentController;


    @Autowired
    UserService userService;



    @Test
    public void test_uploaded_file_can_be_downloaded(){


        User doctor = userService.findByUserName("doctorunknown");

        final MultipartFile multipartFileFrom = getMockedMultipartFile();
        Document document = documentController.uploadFile(doctor.getId(), multipartFileFrom);


        assertThat(document.getUser().getId(),equalTo(doctor.getId()));
        log.info(document.toString());
        assertNotNull(document.getFileName());
        assertNotNull(document.getId());
        assertNotNull(document.getSize());
        assertNotNull(document.getUrl());

        final MockHttpServletRequest request = new MockHttpServletRequest();

        ResponseEntity<?> response = documentController.downloadFile(doctor.getId(), request);

        assertThat(response.getHeaders().getContentType(),equalTo(MediaType.IMAGE_PNG));

    }



    @Test
    public void when_downloadFile_called_with_invalid_userID_exception_should_be_thrown(){


        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            final long invalidUserId = 987L;
            documentController.downloadFile(invalidUserId, new MockHttpServletRequest());
        });

        assertThat(exception.getMessage(),containsString("Invalid User ID"));

    }
    @Test
    public void when_downloadFile_called_with_user_with_no_document_exception_should_be_thrown(){

        User doctorWithNoDocument = userService.findByUserName("doctor");


        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            documentController.downloadFile(doctorWithNoDocument.getId(), new MockHttpServletRequest());
        });

        assertThat(exception.getMessage(),containsString("User does not have Document"));

    }
}