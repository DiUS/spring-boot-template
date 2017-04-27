package com.dius.account.infrastructure.client;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.dius.account.domain.Account;
import com.google.common.collect.ImmutableMap;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class AccountClientTest {

    private final String ACCOUNT_CONSUMMER = "Account_Consumer";
    private final Map<String, String> HEADERS = ImmutableMap.of("Content-Type", "application/json");

    final Account NEW_ACCOUNT = Account.Builder.newBuilder()
            .id(99L)
            .name("NEW ACCOUNT")
            .address("Anywhere")
            .build();

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("Account_Provider", "localhost", 8080, this);

    @Pact(consumer = ACCOUNT_CONSUMMER)
    public PactFragment getFragment(PactDslWithProvider builder) {

        return builder
                .uponReceiving("Get Account By ID")
                .path("/accounts/1")
                .method("GET")

                .willRespondWith()
                .headers(HEADERS)
                .status(HttpStatus.OK.value())
                .toFragment();
    }

    @Pact(consumer = ACCOUNT_CONSUMMER)
    public PactFragment deleteFragment(PactDslWithProvider builder) {
        return builder
                .uponReceiving("Delete Account")
                .path("/accounts/2")
                .method("DELETE")

                .willRespondWith()
                .status(HttpStatus.OK.value())
                .toFragment();
    }

    @Pact(consumer = ACCOUNT_CONSUMMER)
    public PactFragment findAllFragment(PactDslWithProvider builder) {

        return builder
                .uponReceiving("Find all Accounts")
                .path("/accounts")
                .method("GET")

                .willRespondWith()
                .headers(HEADERS)
                .status(HttpStatus.OK.value())
                .toFragment();
    }

    @Pact(consumer = ACCOUNT_CONSUMMER)
    public PactFragment createFragment(PactDslWithProvider builder) {

        return builder
                .uponReceiving("Create new Accounts")
                .path("/accounts")
                .method("POST")
                .headers(HEADERS)
                .body(newAccountJsonBody(NEW_ACCOUNT))

                .willRespondWith()
                .status(HttpStatus.CREATED.value())
                .toFragment();
    }

    @Test
    @PactVerification(fragment = "findAllFragment")
    public void shouldFindAllAccounts() {
        ResponseEntity<List> response = new AccountClient().findAll();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @PactVerification(fragment = "getFragment")
    public void shouldGetAccountById() {
        ResponseEntity<Account> response = new AccountClient().get(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @PactVerification(fragment = "deleteFragment")
    public void shouldDeleteAccount() {
        assertThat((new AccountClient().delete(2L)).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @PactVerification(fragment = "createFragment")
    public void shouldAddAccount() {
        ResponseEntity response = new AccountClient().create(NEW_ACCOUNT);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    private String newAccountJsonBody(Account account) {
        return new PactDslJsonBody()
                .numberType("id", account.getId())
                .stringValue("name", account.getName())
                .stringValue("address", account.getAddress())
                .toString();
    }
}