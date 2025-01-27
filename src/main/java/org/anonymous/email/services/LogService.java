package org.anonymous.email.services;

import lombok.RequiredArgsConstructor;
import org.anonymous.email.constants.AuthStatus;
import org.anonymous.email.entities.Log;
import org.anonymous.email.repositories.LogRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository repository;

    public void logStatus(String to, AuthStatus status) {
        Log log = new Log();

        log.setTo(List.of(to)); // 이메일 수신자
        log.setStatus(status);
        log.setVerificationTime(LocalDateTime.now()); // 상태 기록 시간
        repository.saveAndFlush(log); // 로그 저장
    }
}