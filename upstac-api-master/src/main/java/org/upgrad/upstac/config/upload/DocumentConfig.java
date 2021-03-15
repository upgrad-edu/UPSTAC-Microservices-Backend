package org.upgrad.upstac.config.upload;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class DocumentConfig {
    @Value("${file.upload-dir}")
     String uploadDir;

}
