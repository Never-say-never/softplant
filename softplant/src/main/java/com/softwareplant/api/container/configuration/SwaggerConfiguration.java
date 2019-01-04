package com.softwareplant.api.container.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        //String host = environment.getRequiredProperty("application.apiBaseFullPath");
        String host = "http://localhost:8080";
        if (host.contains("://")) {
            host = host.substring(host.indexOf("://") + 3, host.length());
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .pathProvider(new CustomPathProvider())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
            .build();
    }

    private class CustomPathProvider extends AbstractPathProvider {
        @Override
        protected String applicationPath() {
            return "/";
        }

        @Override
        protected String getDocumentationPath() {
            return "/";
        }
    }
}
