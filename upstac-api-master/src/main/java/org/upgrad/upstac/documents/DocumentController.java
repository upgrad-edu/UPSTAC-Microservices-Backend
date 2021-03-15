package org.upgrad.upstac.documents;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.users.UserService;
import org.upgrad.upstac.users.credentials.ChangePasswordService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;


@RestController
@RequestMapping("/documents")
@Slf4j
public class DocumentController {




    @Autowired
    UserLoggedInService userLoggedInService;


    @Autowired
    ChangePasswordService changePasswordService;


    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload/{id}")
    public Document uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {

        try {
            return documentService.save(id, file);
        } catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id, HttpServletRequest request) {


        try {
            Resource resource = documentService.loadFileAsResource(id);
            String contentType = getContentType(request, resource);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }

    String getContentType(HttpServletRequest request, Resource resource) {
        String contentType = null;

            contentType = request.getServletContext().getMimeType(getAbsolutePath(resource));

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

     String getAbsolutePath(Resource resource) {
        try {
            return resource.getFile().getAbsolutePath();
        } catch (IOException ignore) {
            return null;
        }
    }

}
