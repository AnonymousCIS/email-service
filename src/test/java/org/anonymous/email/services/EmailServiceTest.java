package org.anonymous.email.services;

import org.anonymous.email.controllers.RequestEmail;
import org.anonymous.email.entities.EmailLog;
import org.anonymous.email.repositories.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;
    @Autowired
    private LogRepository repository;

    private MockMultipartFile file1;
    private MockMultipartFile file2;

    @BeforeEach
    void init() {
        file1 = new MockMultipartFile("file", "test1.png", MediaType.IMAGE_PNG_VALUE, new byte[] {0, 1, 2, 3});
        file2 = new MockMultipartFile("file", "test2.png", MediaType.IMAGE_PNG_VALUE, new byte[] {0, 1, 2, 3});
    }

    @Test
    void test1() {
        RequestEmail form = new RequestEmail();
        form.setTo(List.of("ckxodlf12@naver.com"));
        form.setSubject("테스트 이메일 전송");
        form.setContent("테스트 이메일 내용...");
        // form.setFiles(List.of(file1, file2));

        emailService.sendEmail(form, "general");
    }
    @Test
    void test2() {

        List<EmailLog> item = repository.findAll();
        System.out.println(item);
    }
//    @Test
//    void Test2(){
//
//    }
}