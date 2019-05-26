package com.linln.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @author lovec
 */
@Configuration
public class ResourcesConfigAdapter implements WebMvcConfigurer  {

    @Value("${project.upload.file-path}")
    private String resourceDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+resourceDir);
    }
}
