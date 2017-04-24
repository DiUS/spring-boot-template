package com.dius.application.controllers;


import com.dius.account.Application;
import com.dius.account.application.presentation.AccountModel;
import com.dius.account.domain.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

    private String resourceUrl;

    @Autowired
    RestTemplate restTemplate;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setup() {
        resourceUrl = "http://localhost:" + port + "/accounts";
    }

    @Test
    public void shouldCreateNewAccount() {
        Account account = new Account(10L, "John", "99 Queen street");
        ResponseEntity<Account> response = restTemplate.postForEntity(resourceUrl, account, Account.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = response.getHeaders().getLocation();
        assertThat(location).isNotNull();
    }

    @Test
    public void shouldGetAccountById() {
        ResponseEntity<AccountModel> responseEntity = restTemplate.getForEntity(resourceUrl + "/1", AccountModel.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        AccountModel account = responseEntity.getBody();
        assertThat(account.getName()).isEqualTo("Nicole");
        assertThat(account.getAddress()).isEqualTo("99 Queen st");
    }

    @Test
    public void shouldGetAll() {
        ResponseEntity<List> response = restTemplate.getForEntity(resourceUrl, List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isNotEqualTo(0);
    }

    @Test
    public void shouldUpdateAccount() {
        AccountModel model = Account.Builder.newBuilder().name("Name changed")
                .address("Address changed")
                .build().toModel();

        restTemplate.put(resourceUrl + "/1", model);

        ResponseEntity<AccountModel> response = restTemplate.getForEntity(resourceUrl + "/1", AccountModel.class);
        AccountModel expectedAccount = response.getBody();
        assertThat(expectedAccount.getName()).isEqualTo("Name changed");
        assertThat(expectedAccount.getAddress()).isEqualTo("Address changed");
    }

    @Test(expected = HttpClientErrorException.class)
    public void shouldDeleteAccount() {
        Account account = new Account(99L, "New Record", "whatever");
        ResponseEntity<Account> response = restTemplate.postForEntity(resourceUrl, account, Account.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        restTemplate.delete(resourceUrl + "/99");

        // Should trigger ClientErrorException as entity not found
        restTemplate.getForEntity(resourceUrl + "/99", AccountModel.class);
    }
}
