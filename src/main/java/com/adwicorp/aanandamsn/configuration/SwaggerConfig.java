package com.adwicorp.aanandamsn.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("SocioNet API")
                        .description("API documentation for SocioNet application")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ina Kachhal")
                                .email("ina.kachhal@gmail.com")
                                .url("https://socionet.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }

    @Bean
    public OperationCustomizer customGlobalHeader() {
        return (operation, name) -> {
            // Create the X-Trace-ID header parameter
            Parameter traceIdHeader = new Parameter()
                    .in(ParameterIn.HEADER.toString()) // Indicates it's a header parameter
                    .name("X-Trace-ID")
                    .description("The trace ID for the request")
                    .required(false) // Set to true if it's mandatory
                    .schema(new io.swagger.v3.oas.models.media.StringSchema()); // Schema for the header

            // Add the parameter to the operation
            operation.addParametersItem(traceIdHeader);
            return operation;
        };
    }
}

