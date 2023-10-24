package com.async_email.practice.domain.account.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountRegisterRequest {

    private String email;

    public AccountRegisterRequest(final String email) {
        this.email = email;
    }
}
