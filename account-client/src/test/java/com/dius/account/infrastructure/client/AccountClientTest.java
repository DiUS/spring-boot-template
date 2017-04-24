package com.dius.account.infrastructure.client;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.model.PactFragment;
import com.dius.account.domain.Account;
import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;

public class AccountClientTest {

    @Rule
    public PactRule rule = new PactRule("localhost", 8080, this);

    @Pact(state = "Account_State", provider = "Account_Provider", consumer = "Account_Consumer")
    public PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider.PactDslWithState builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return builder
                .uponReceiving("Get all Accounts request")
                    .path("/accounts")
                    .method("GET")
                .willRespondWith()
                    .headers(headers)
                    .status(200)
                    .body("[{\"id\":1, \"name\": \"Nicole\", \"address\": \"99 Queen st\"}]")
                .toFragment();
    }

    @Test
    @PactVerification("Account_State")
    public void runTest() {
        ResponseEntity<List> response = new AccountClient().findAll();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}