package org.anonymous.email.services;

import com.querydsl.core.BooleanBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.constants.AuthStatus;
import org.anonymous.email.entities.Log;
import org.anonymous.email.entities.QLog;
import org.anonymous.email.repositories.LogRepository;
import org.anonymous.member.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Lazy
@Service
@Transactional
@RequiredArgsConstructor
public class LogService {
    private final LogRepository repository;
    private final MemberUtil memberUtil;
    public void logStatus(String to, AuthStatus status, LocalDateTime time) {
        Log log = new Log();
        log.setTo(to);
        log.setStatus(status);
        log.setRequestTime(time);
        if (status == AuthStatus.REQUESTED){
            log.setVerificationTime(null);
        } else {
            log.setVerificationTime(time);
        }


        repository.saveAndFlush(log);
    }


    public List<Long> getLog(AuthStatus status){
        if (!memberUtil.isAdmin()){
            return List.of();
        }


    }
}