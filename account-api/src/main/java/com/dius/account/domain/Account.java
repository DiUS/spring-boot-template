package com.dius.account.domain;

import com.dius.account.application.presentation.AccountModel;

public class Account {

    private Long id;
    private String name;
    private String address;

    public Account(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Account() {
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

    public AccountModel toModel() {
        return new AccountModel(this.id, this.name, this.address);
    }

    public Account(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String address;

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder copy(AccountModel request) {
            this.name = request.getName();
            this.address = request.getAddress();
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

}
