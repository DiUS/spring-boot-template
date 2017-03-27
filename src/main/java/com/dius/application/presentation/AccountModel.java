package com.dius.application.presentation;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountModel {

    private Long id;

    private String name;

    private String address;

    @JsonCreator
    public AccountModel(@JsonProperty("id") Long id,
                        @JsonProperty("name") String name,
                        @JsonProperty("address") String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


}
