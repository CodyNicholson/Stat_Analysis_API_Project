package com.statanalysisapi.config;

import com.statanalysisapi.StatAnalysisApiApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class SpringConfiguration {
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(StatAnalysisApiApplication.class);
    }

    @Bean //NOTE: Used to render swagger-ui
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}
