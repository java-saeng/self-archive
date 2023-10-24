package com.async_email.practice.domain.account.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private boolean emailAuth; //이메일 인증 여부

    public Account(final String email, final boolean emailAuth) {
        this.email = email;
        this.emailAuth = emailAuth;
    }

    /**
     * 이메일 인증 완료 시 해당 메서드 호출
     */
    public void accessEmailAuth() {
        emailAuth = true;
    }
}
