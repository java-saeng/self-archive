package com.async_email.practice.domain.email.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmailAuthResponse {

    private String email;
    private String authToken;

    public EmailAuthResponse(final String email, final String authToken) {
        this.email = email;
        this.authToken = authToken;
    }
}
