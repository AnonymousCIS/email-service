package org.anonymous.email.services;

import org.anonymous.email.constants.AuthStatus;
import org.anonymous.email.entities.Log;
import org.anonymous.email.repositories.LogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class LogServiceTest {

    @Autowired
    private LogService logService;

    @Autowired
    private LogRepository logRepository;

    @Test
    void Test1() {
        String email = "ckxodlf12@naver.com";
        AuthStatus status = AuthStatus.REQUESTED;
        LocalDateTime requestTime = LocalDateTime.now();

        logService.logStatus(email, status, requestTime);

        List<Log> logs = logRepository.findAll();
        System.out.println(logs);
    }
}
