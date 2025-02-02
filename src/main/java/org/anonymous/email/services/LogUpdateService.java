package org.anonymous.email.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.constants.AuthStatus;
import org.anonymous.email.controllers.RequestEmail;
import org.anonymous.email.entities.EmailLog;
import org.anonymous.email.repositories.LogRepository;
import org.anonymous.member.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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


//    public void logStatus( String to, String subject, String content, LocalDateTime time) {
//        EmailLog emailLog = new EmailLog();
//        emailLog.setTo(to);
////        emailLog.setStatus(status);
////        emailLog.setRequestTime(time);
////
////            emailLog.setVerificationTime(null);
////
////            emailLog.setVerificationTime(time);
//        emailLog.setSubject(subject);
//        emailLog.setContent(content);
//        emailLog.setCreatedAt(time);
//
//
//        repository.saveAndFlush(emailLog);
//    }


//    public List<Long> getLog(AuthStatus status){
//        if (!memberUtil.isAdmin()){
//            return List.of();
//        }
//
//        return null;
//    }
}