package org.upgrad.upstac.documents;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.upgrad.upstac.config.upload.DocumentConfig;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.UserService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentService {

    private String fileStorageLocation = null;

    UserService userService;

    DocumentRepository documentRepository;


    @Autowired
    public DocumentService(DocumentConfig documentConfig, UserService userService, DocumentRepository documentRepository) {
        this.fileStorageLocation = (System.getProperty("user.home") + File.separator + documentConfig.getUploadDir());
        this.userService = userService;
        this.documentRepository = documentRepository;
        createDirectory(this.fileStorageLocation);



    }

    public static void createDirectory(String fileStorageLocation)  {
        try {
            Files.createDirectories(Paths.get(fileStorageLocation));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public String storeFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            String result = this.fileStorageLocation + File.separator + fileName;

            Path targetLocation = Paths.get(result);

            copyFiles(file, targetLocation);

            return fileName;
        } catch (IOException ex) {
            throw new AppException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void copyFiles(MultipartFile file, Path targetLocation) throws IOException {
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    public Document save(Long userId, MultipartFile file) {

        User user = userService.findById(userId).orElseThrow(() -> new AppException("Invlid User ID"));

        String fileName = storeFile(file);

        String fileDownloadUri = "/documents/download/" + user.getId();


        Document document = documentRepository.findByUser(user).orElse(new Document(user));

        document.setUrl(fileDownloadUri);
        document.setContentType(file.getContentType());
        document.setFileName(fileName);
        document.setSize(file.getSize());

        return documentRepository.save(document);
    }

    public Resource loadFileAsResource(Long userId) {


        User user = userService.findById(userId).orElseThrow(() -> new AppException("Invalid User ID"));

        Document document = documentRepository.findByUser(user).orElseThrow(() -> new AppException("User does not have Document"));

        String fileName = document.getFileName();

        String result = this.fileStorageLocation + File.separator + fileName;
        Path filePath = Paths.get(result);
        return getResource(filePath);


    }

    public UrlResource getResource(Path filePath) {
        UrlResource resource;
        try {
            resource = createUrlResource(filePath.toUri());
            if (resource.exists())
                return resource;

        } catch (MalformedURLException ignore) {

        }

        throw new AppException("Invalid File " + filePath);

    }

    public UrlResource createUrlResource(URI uri) throws MalformedURLException {
        return new UrlResource(uri);
    }

    public List<Document> getPendingApprovals() {
        Set<User> users = userService.findPendingApprovals().stream().collect(Collectors.toSet());

        return documentRepository.findAllByUserIn(users);

    }

}
