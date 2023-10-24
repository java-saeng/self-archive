package com.async_email.practice.domain.email.dao;

import com.async_email.practice.domain.email.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {

    @Query("select e from EmailAuth e" +
            " where e.email = :email" +
            " and e.authToken = :authToken" +
            " and e.expiredDate >= :currentTime" +
            " and e.isExpired = false")
    Optional<EmailAuth> findWaitingConfirmedEmail(@Param("email") String email,
                                                  @Param("authToken") String authToken,
                                                  @Param("currentTime") LocalDateTime currentTime);
}
