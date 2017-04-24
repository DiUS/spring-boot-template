package com.dius.account.application.controllers;


import com.dius.account.application.presentation.AccountModel;
import com.dius.account.domain.Account;
import com.dius.account.infrastructure.AccountRepository;
import jersey.repackaged.com.google.common.base.Throwables;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import static java.util.Optional.ofNullable;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/accounts")
public class AccountController {

    private AccountRepository accountRepository;

    private Random idGenerator;

    @Inject
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        idGenerator = new Random();
    }

    @GET
    public Response all() {
        return Response.ok(accountRepository.all()).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Account account = accountRepository.get(id);
        return Response.ok(account.toModel()).build();
    }

    @POST
    public Response create(@RequestBody AccountModel request) {
        Account account = new Account(Integer.toUnsignedLong(idGenerator.nextInt(10)), request.getName(), request.getAddress());
        accountRepository.create(account);
        URI createdLocation;
        try {
            createdLocation = new URI("/accounts/" + account.getId());
        } catch (URISyntaxException e) {
            throw Throwables.propagate(e);
        }
        return Response.created(createdLocation).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") final Long id, @RequestBody AccountModel request) {
        ofNullable(accountRepository.get(id)).orElseThrow(NotFoundException::new);
        final Account updatedAccount = Account.Builder.newBuilder()
                .copy(request)
                .id(id)
                .build();

        accountRepository.update(updatedAccount);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final Long id) {
        accountRepository.remove(id);
        return Response.ok().build();
    }

}
