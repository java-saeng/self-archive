package com.async_email.practice.domain.email.application;

import com.async_email.practice.domain.account.dao.AccountRepository;
import com.async_email.practice.domain.account.entity.Account;
import com.async_email.practice.domain.email.api.model.EmailAuthRequest;
import com.async_email.practice.domain.email.api.model.EmailAuthResponse;
import com.async_email.practice.domain.email.dao.EmailAuthRepository;
import com.async_email.practice.domain.email.entity.EmailAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailAuthRepository emailAuthRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public EmailAuthResponse confirmEmail(EmailAuthRequest requestDto) {
        final String email = requestDto.getEmail();
        final String authToken = requestDto.getAuthToken();

        final EmailAuth emailAuth = emailAuthRepository.findWaitingConfirmedEmail(email,
                                                                                  authToken,
                                                                                  LocalDateTime.now())
                                                       .get();

        final Account account = accountRepository.findByEmail(email).get();

        account.accessEmailAuth();
        emailAuth.expire();

        return new EmailAuthResponse(email, authToken);
    }
}
