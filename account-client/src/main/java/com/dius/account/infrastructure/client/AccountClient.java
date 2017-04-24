package com.dius.account.infrastructure.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AccountClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080";

    private RestTemplate restTemplate;

    public AccountClient() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<List> findAll() {
        return restTemplate.getForEntity(REST_SERVICE_URI + "/accounts", List.class);
    }
}
