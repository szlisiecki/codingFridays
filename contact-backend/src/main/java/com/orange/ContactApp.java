package com.orange;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;

@SpringBootApplication
@EnableSwagger2
public class ContactApp {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ContactApp.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
    public Docket get() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.orange"))
                .build().apiInfo(createApiInfo());
    }
	
	private ApiInfo createApiInfo() {
        return new ApiInfo("Contacts API",
                "REST API for managing contacts",
                "1.00",
                "",
                new Contact("Szymon", "http://orange.com", "szymon@orange.pl"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html",
                Collections.emptyList()
        );
    }
}
