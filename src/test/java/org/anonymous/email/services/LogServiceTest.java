package org.anonymous.email.services;

import org.anonymous.email.constants.AuthStatus;
import org.anonymous.email.controllers.LogSearch;
import org.anonymous.email.entities.Log;
import org.anonymous.email.repositories.LogRepository;
import org.anonymous.global.paging.ListData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class LogServiceTest {

    @Autowired
    private LogUpdateService logService;
    @Autowired
    private LogInfoService logInfoService;

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
    @Test
    void Test2() {
        LogSearch search = new LogSearch();
        search.setSopt("TO");
        search.setSkey("ckxodlf12@naver.com");
        search.setLimit(1);
        search.setPage(10);

        ListData<Log> result = logInfoService.getList(search);

        System.out.println(result);
    }
}
