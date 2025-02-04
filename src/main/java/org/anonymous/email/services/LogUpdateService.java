package org.anonymous.email.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.controllers.RequestEmail;
import org.anonymous.email.entities.EmailLog;
import org.anonymous.email.repositories.LogRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@Transactional
@RequiredArgsConstructor
public class LogUpdateService {
    private final LogRepository repository;

    public void logStatus(RequestEmail form){
        EmailLog log = new EmailLog();
        log.setTo(form.getTo().toString());
        log.setSubject(form.getSubject());
        log.setContent(form.getContent());
        log.setCreatedAt(form.getCreatedAt());

        repository.saveAndFlush(log);
    }
}