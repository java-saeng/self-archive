package com.async_email.practice.domain.email.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class EmailAuthRequest {

    private String email;
    private String authToken;

    public EmailAuthRequest(final String email, final String authToken) {
        this.email = email;
        this.authToken = authToken;
    }
}
