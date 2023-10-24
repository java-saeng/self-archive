package com.async_email.practice.domain.email.api;

import com.async_email.practice.domain.email.api.model.EmailAuthRequest;
import com.async_email.practice.domain.email.api.model.EmailAuthResponse;
import com.async_email.practice.domain.email.application.EmailAuthService;
import com.async_email.practice.domain.email.application.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EmailController {

    private final EmailAuthService emailAuthService;
    private final EmailService emailService;

    @GetMapping("/confirm")
    public EmailAuthResponse confirm(@ModelAttribute EmailAuthRequest request) {
        return emailService.confirmEmail(request);
    }
}
