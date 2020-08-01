package com.udacity.jwdnd.course1.cloudstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}
//	@Bean
//	public MultipartResolver multipartResolver() {
//		CommonsMultipartResolver multipartResolver
//				= new CommonsMultipartResolver();
//		multipartResolver.setMaxUploadSize(1048576);
//		return multipartResolver;
//	}
}
