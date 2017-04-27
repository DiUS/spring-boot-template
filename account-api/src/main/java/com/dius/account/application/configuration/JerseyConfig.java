package com.dius.account.application.configuration;

import com.dius.account.application.controllers.AccountController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        // Manually register resource to avoid
        // java.io.FileNotFoundException: xyz.jar!/BOOT-INF/classes
        // when running flat jar. For more info, visit https://github.com/spring-projects/spring-boot/issues/1468
        register(AccountController.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

}
