package com.async_email.practice.domain.email.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class EmailAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    //인증을 여러 개 받을 경우 어떤 인증 요청인지 구분하기 위한 column
    private String authToken;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean isExpired;

    private LocalDateTime expiredDate;

    @Builder
    public EmailAuth(final String email, final String authToken, final boolean isExpired) {
        this.email = email;
        this.authToken = authToken;
        this.isExpired = isExpired;
        this.expiredDate = LocalDateTime.now().plusMinutes(5L);
    }

    public void expire() {
        isExpired = true;
    }
}
