package com.linln.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lovec
 */
@Configuration
@Component
@Data
public class FileResourceConfig {


    @Value("${project.upload.file-path}")
    public String uploadDir;

    @Value("${project.upload.clockin-path}")
    public String clockinPhotoDir;
}
