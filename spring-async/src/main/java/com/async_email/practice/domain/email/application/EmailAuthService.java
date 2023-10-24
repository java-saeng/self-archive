package com.async_email.practice.domain.email.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailAuthService {

    private final JavaMailSender javaMailSender;

    @Async
    public void send(String email, String authToken) {
        MimeMessage mail = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");
            mailHelper.setFrom("sender");
            mailHelper.setTo(email);
            mailHelper.setSubject("회원가입 이메일 인증");
            mailHelper.setText("http://localhost:8080/api/confirm?email=" + email + "&authToken=" + authToken, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        javaMailSender.send(mail);
    }

    @Async
    public void asyncTest() {
        log.info("async Test");
    }
}
