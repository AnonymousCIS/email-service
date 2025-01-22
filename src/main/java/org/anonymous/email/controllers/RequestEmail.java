package org.anonymous.email.controllers;

import lombok.Data;

import java.util.List;

@Data
public class RequestEmail {
    // 수신 이메일
    private List<String> to;

    // 참조 이메일
    private List<String> cc;

    // 숨은 참조 이메일
    private List<String> bcc;

    // 메일 제목
    private String subject;

    // 메일 내용
    private String content;
}
