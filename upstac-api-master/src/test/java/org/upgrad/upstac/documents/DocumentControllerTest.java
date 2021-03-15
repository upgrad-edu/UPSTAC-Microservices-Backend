package org.upgrad.upstac.documents;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.users.credentials.ChangePasswordService;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {


    @Mock
    UserLoggedInService userLoggedInService;


    @Mock
    ChangePasswordService changePasswordService;


    @Mock
    private DocumentService documentService;


    @InjectMocks
    DocumentController documentController;

    @Test
    public void when_documentService_save_throws_error_uploadFile_should_throw_badRequest() throws IOException {

        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        when(documentService.save(238L,multipartFile)).thenThrow(new AppException("Invalid ID") );

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,()->{
            documentController.uploadFile(238L,multipartFile);
        });

        assertThat(exception.getStatus(), equalTo(HttpStatus.BAD_REQUEST));
        assertThat(exception.getMessage(), containsString("Invalid ID"));

    }
    @Test
    public void getAbsolutePath_should_return_null_if_file_not_found() throws IOException {


        Resource resource = Mockito.mock(Resource.class);
        when(resource.getFile()).thenThrow(new IOException("Invalid File"));

        String result = documentController.getAbsolutePath(resource);
        assertNull(result);
    }

    @Test
    public void getContentType_should_return_octetStream_if_resource_throwsException() throws IOException {


        Resource resource = Mockito.mock(Resource.class);
        when(resource.getFile()).thenThrow(new IOException("Invalid File"));

        String result = documentController.getContentType(new MockHttpServletRequest(), resource);
        assertThat(result, equalTo("application/octet-stream"));
    }

}