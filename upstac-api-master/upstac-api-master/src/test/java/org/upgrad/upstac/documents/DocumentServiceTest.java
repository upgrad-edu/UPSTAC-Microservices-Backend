package org.upgrad.upstac.documents;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.upload.DocumentConfig;
import org.upgrad.upstac.exception.AppException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.upgrad.upstac.shared.FileReader.getMultipartFileFrom;
import static org.upgrad.upstac.testutils.TestDataGenerator.getMockedMultipartFile;

@Slf4j
class DocumentServiceTest {

    @Test
    public void create_folders_if_not_exists() {
        DocumentService documentService = getDocumentService();

        documentService.storeFile(getMockedMultipartFile());

    }

    public DocumentService getDocumentService() {
        DocumentConfig documentConfig = new DocumentConfig();
        documentConfig.setUploadDir("test-usr-uploads");

        return new DocumentService(documentConfig, null, null);
    }

    @Test
    public void when_copying_files_is_error_storeFile_should_throw_exception() throws IOException {
        DocumentService documentService = Mockito.spy(getDocumentService());

        final String errorMessage = "Unable to copy";
        doThrow(new IOException(errorMessage)).when(documentService).copyFiles(any(), any());


        AppException exception = assertThrows(AppException.class, () -> {
            documentService.storeFile(getMockedMultipartFile());
        });


        assertThat(exception.getMessage(), containsString("Could not store file"));

    }

    @Test
    public void creatingInvalidDirectoryShouldSilentlyIgnore() {

        DocumentService.createDirectory("dsjsha@@!^&^@!&@*!/@!@^!&^");

    }
    @Test
    public void getResourceWIthMalformedUrlShouldThrowException() throws MalformedURLException {

          DocumentService documentService = Mockito.spy(getDocumentService());

        final String errorMessage = "Invalid URL";
        doThrow(new MalformedURLException(errorMessage)).when(documentService).createUrlResource(any());

        String filePath =System.getProperty("user.home") + File.separator + "testuploads";
        Path path = Paths.get(filePath);

        AppException exception = assertThrows(AppException.class, () -> {
            documentService.getResource(path);
        });


    }

    @Test
    public void getResourceWIthNotExistingPathShouldThrowException() throws MalformedURLException {

          DocumentService documentService = Mockito.spy(getDocumentService());


        String filePath =System.getProperty("user.home") + File.separator + "testinvaliduploads";
        Path path = Paths.get(filePath);

        AppException exception = assertThrows(AppException.class, () -> {
            documentService.getResource(path);
        });


    }
}