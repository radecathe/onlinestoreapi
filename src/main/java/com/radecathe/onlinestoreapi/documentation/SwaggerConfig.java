package com.radecathe.onlinestoreapi.documentation;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
	@Bean
    public Docket apiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.radecathe.onlinestoreapi.controller"))
                    .paths(PathSelectors.any())
                    .build()
                    .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageForMethod())
                .globalResponseMessage(RequestMethod.POST, responseMessageForMethod())
                .globalResponseMessage(RequestMethod.PUT, responseMessageForMethod())
                .globalResponseMessage(RequestMethod.DELETE, responseMessageForMethod())
                .apiInfo(metaData())
                .securityContexts(Arrays.asList(actuatorSecurityContext()))
                .securitySchemes(Arrays.asList(basicAuthScheme()));
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Online Store API")
                .description("Example of an online store API, developed using:<ul><li>Spring Boot</li><li>MySQL database (RDS service on AWS cloud)</li><li>Linux server (EC2 service on AWS cloud)</li><li>Docker container</li></ul><p>All operations require basic authentication. The <b>user-controller</b> API was created to manage access users. Anyway, a user is created to access all operations:</p><ul><li>User: <b>admin</b></li><li>Password: <b>admin</b></li></ul><p>To access the API source code (GitHub repository), click <a href=\"\">here</a>.</p>")
                .version("1.0.0")
                .contact(new Contact("Raphael Theodoro", null, null))
                .build();
    }
    
    @SuppressWarnings("serial")
	private ArrayList<ResponseMessage> responseMessageForMethod() {
        return new ArrayList<ResponseMessage>() {{
        	 
        	add(new ResponseMessageBuilder()
                .code(500)
                .message("Internal Server Error")
                .build());
            add(new ResponseMessageBuilder()
                .code(401)
                .message("Unauthorized")
                .build());
            add(new ResponseMessageBuilder()
                .code(403)
                .message("Forbidden")
                .build());
            add(new ResponseMessageBuilder()
                .code(400)
                .message("Bad Request")
                .build());
            add(new ResponseMessageBuilder()
                .code(404)
                .message("Not Found")
                .build());
        }};
    }
    
    private SecurityContext actuatorSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(basicAuthReference()))
                .forPaths(PathSelectors.any())
                .build();
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }
}