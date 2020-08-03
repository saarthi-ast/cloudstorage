package com.udacity.jwdnd.course1.cloudstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.MAX_FILE_SIZE;

/**
 * The type Cloud storage application.
 * @author Sudhir Tyagi
 */
@SpringBootApplication
public class CloudStorageApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

    /**
     * Multipart resolver multipart resolver.
     *
     * @return the multipart resolver
     */
    @Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver
				= new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(MAX_FILE_SIZE);
		return multipartResolver;
	}
}
