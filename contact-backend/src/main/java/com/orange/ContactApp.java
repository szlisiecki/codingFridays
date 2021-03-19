package com.orange;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import liquibase.integration.spring.SpringLiquibase;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiModelReader;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ContactApp {

	//TODO refresh token
	//TODO black/white list
	//TODO użytkownicy w bazie danych
	//TODO refactor
	//TODO liquibase
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ContactApp.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Primary
	@Bean
	public ApiListingScanner addExtraOperations(ApiDescriptionReader apiDescriptionReader, ApiModelReader apiModelReader, DocumentationPluginsManager pluginsManager)
	{
	    return new FormLoginOperations(apiDescriptionReader, apiModelReader, pluginsManager);
	}
	@Bean
    public Docket get() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.orange"))
//                .apis(RequestHandlerSelectors.any())              
//                .paths(PathSelectors.any())  
                .build().apiInfo(createApiInfo());
	}

	private ApiInfo createApiInfo() {		
		Contact contact = new Contact("312 Plaza", "https://plazza.orange.com/groups/312", "plaza312@orange.com");
		return new ApiInfo("Contacts Documentation",
									  "Rest application for storing contacts", 
									  "1-SNAPSHOT", 
									  null, 
									  contact, 
									  "MIT Licence", 
									  "https://opensource.org/licenses/mit-license.php",
									  Collections.emptyList()); 
	}
}
