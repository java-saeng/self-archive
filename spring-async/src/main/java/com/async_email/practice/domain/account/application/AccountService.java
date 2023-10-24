package com.async_email.practice.domain.account.application;

import com.async_email.practice.domain.account.api.model.AccountRegisterRequest;
import com.async_email.practice.domain.account.dao.AccountRepository;
import com.async_email.practice.domain.account.entity.Account;
import com.async_email.practice.domain.email.application.EmailAuthService;
import com.async_email.practice.domain.email.dao.EmailAuthRepository;
import com.async_email.practice.domain.email.entity.EmailAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final EmailAuthRepository emailAuthRepository;

    private final EmailAuthService emailAuthService;

    @Transactional
    public Account registerAccount(AccountRegisterRequest requestDto) {
        final String email = requestDto.getEmail();

        //이메일 인증 생성
        final EmailAuth emailAuth = emailAuthRepository.save(
                EmailAuth.builder()
                         .email(email)
                         .authToken(UUID.randomUUID().toString()) //UUID를 통해 어떤 인증인지 구분
                         .isExpired(false)
                         .build(
                         ));

        final Account account = accountRepository.save(new Account(email, false));

        //메일 전송
        emailAuthService.send(email, emailAuth.getAuthToken());

        return account;
    }


}
