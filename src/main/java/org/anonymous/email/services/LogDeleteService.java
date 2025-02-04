package org.anonymous.email.services;

import lombok.RequiredArgsConstructor;
import org.anonymous.email.entities.EmailLog;
import org.anonymous.email.entities.QEmailLog;
import org.anonymous.email.repositories.LogRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class LogDeleteService {
    private final LogRepository repository;

    /**
     * 1년 마다 오전 12시에 삭제
     * @return
     */

    @Scheduled(cron = "0 0 0 * * *")
    public void delete(){
        QEmailLog emailLog = QEmailLog.emailLog;
        List<EmailLog> items = (List<EmailLog>)repository.findAll(emailLog.createdAt.before(LocalDateTime.now().minusYears(1L)));
        if (!items.isEmpty()) {
            repository.deleteAll(items);
            repository.flush();
        }
    }
}