package org.dbiagi.marketplace.config;

import org.dbiagi.marketplace.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;

// @TODO springfox project and data rest integration is broken, check their github in the future to try integrate again

@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {
    private ApiInfo getMetadata() {
        return new ApiInfoBuilder()
            .title("Marketplace REST API")
            .description("Marketplace REST API")
            .version("v1.0.0")
            .build();
    }

    @Bean
    Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(Application.class.getPackage().getName()))
            .paths(any())
            .build()
            .apiInfo(getMetadata());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
