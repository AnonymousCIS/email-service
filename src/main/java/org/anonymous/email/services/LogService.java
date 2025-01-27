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

    public void logStatus(String to, AuthStatus status, LocalDateTime time) {
        Log log = new Log();
        log.setTo(to);
        log.setStatus(status);
        if (status == AuthStatus.REQUESTED){
            log.setRequestTime(time);
        } else {
            log.setVerificationTime(time);
        }

        log.setVerificationTime(LocalDateTime.now());

        repository.saveAndFlush(log);
    }
}