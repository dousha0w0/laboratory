/*
 * @(#) SwaggerConfiguration.java 2020-11-15
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author senior
 */
@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfiguration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("RESTFul API").description("接口文档").version("1.0").build()).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.senior.console.api.controller"))
                .paths(PathSelectors.any()).build();
    }
}
