package com.dius.account.infrastructure.client;

import com.dius.account.domain.Account;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AccountClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080/accounts";

    private RestTemplate restTemplate;

    public AccountClient() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<List> findAll() {
        return restTemplate.getForEntity(REST_SERVICE_URI, List.class);
    }

    public ResponseEntity<Account> get(Long id) {
        return restTemplate.getForEntity(REST_SERVICE_URI + "/" + id, Account.class);
    }

    public ResponseEntity create(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Account> entity = new HttpEntity<Account>(account, headers);
        return restTemplate.postForEntity(REST_SERVICE_URI, entity, Account.class);
    }

    public ResponseEntity delete(Long id) {
        restTemplate.delete(REST_SERVICE_URI + "/" + id);
        return ResponseEntity.ok().build();
    }
}