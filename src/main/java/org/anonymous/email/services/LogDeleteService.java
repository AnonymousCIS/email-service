package org.anonymous.email.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.entities.Log;
import org.anonymous.email.repositories.LogRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Lazy
@Service
@EnableScheduling
@RequiredArgsConstructor
public class LogDeleteService {
    private final LogRepository repository;

    /**
     * 1년 마다 오후 12시에 자동삭제
     * @return
     */
    @Async
    @Transactional
    @Scheduled(cron = "0 0 12 * * *")
    public Log delete(){
        repository.deleteByTimeStamp(LocalDateTime.now().minusYears(1L));

        return null;
    }
}