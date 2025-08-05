package com.hermann.cepservice.config;

import jakarta.validation.Validator;
import jakarta.validation.Validation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public org.springframework.validation.Validator springValidator() {
        return new LocalValidatorFactoryBean();
    }
}
