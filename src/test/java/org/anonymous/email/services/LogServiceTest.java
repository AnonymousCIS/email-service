package org.anonymous.email.services;

import com.netflix.discovery.converters.Auto;
import jakarta.mail.internet.MimeMessage;
import org.anonymous.email.constants.AuthStatus;
import org.anonymous.email.controllers.LogSearch;
import org.anonymous.email.controllers.RequestEmail;
import org.anonymous.email.entities.EmailLog;
import org.anonymous.email.repositories.LogRepository;
import org.anonymous.global.paging.ListData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class LogServiceTest {

    @Autowired
    private EmailService emailService;
    @Autowired
    private LogUpdateService service;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private LogInfoService logInfoService;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogDeleteService deleteService;

    @Test
    void test1() throws Exception {
        /**
         * to : 받는 이메일
         * cc : 참조
         * bcc : 숨은 참조
         */
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
        helper.setTo("ckxodlf12@naver.com");
        helper.setSubject("테스트 이메일 제목...");
        helper.setText("테스트 이메일 내용...");
        javaMailSender.send(message);
    }

    @Test
    void test2() {
        Context context = new Context();
        context.setVariable("subject", "테스트 제목...");

        String text = templateEngine.process("email/auth", context);

        System.out.println(text);
    }

    @Test
    void test3() throws Exception {
        RequestEmail form = new RequestEmail();
        form.setTo(List.of("user01@test.org", "user01@test.org"));
        form.setCc(List.of("ckxodlf12@naver.com"));
        form.setBcc(List.of("ckxodlf12@naver.com"));
        form.setSubject("테스트 이메일 제목...");
        form.setContent("<h1>테스트 이메일 내용...</h1>");
        form.setCreatedAt(LocalDateTime.now());
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("key1", "값1");
        tplData.put("key2", "값2");
        service.logStatus(form);
        boolean result = emailService.sendEmail(form, "auth", tplData);
        System.out.println(result);

        List<EmailLog> items = logRepository.findAll();

        System.out.println(items);
    }
    @Test
    void test4() throws Exception {
        LogSearch search = new LogSearch();
        search.setSopt("TO");
        search.setSkey("user01@test.org");
        search.setLimit(1);
        search.setPage(10);

        ListData<EmailLog> result = logInfoService.getList(search);

        System.out.println(result);

    }
    @Test
    @Scheduled(cron = "0 0 0 * * *")
    void Test5() throws Exception{
        deleteService.delete();
    }
}