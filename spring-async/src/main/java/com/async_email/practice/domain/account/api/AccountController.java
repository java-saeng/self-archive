package com.async_email.practice.domain.account.api;

import com.async_email.practice.domain.account.api.model.AccountRegisterRequest;
import com.async_email.practice.domain.account.application.AccountService;
import com.async_email.practice.domain.account.entity.Account;
import com.async_email.practice.domain.email.application.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;
    private final EmailAuthService emailAuthService;

    @PostMapping("/register")
    public Account register(@RequestBody AccountRegisterRequest request) {
        return accountService.registerAccount(request);
    }
}
