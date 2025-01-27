package org.anonymous.email.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.email.constants.AuthStatus;
import org.anonymous.global.entities.BaseEntity;
import org.anonymous.global.entities.BaseMemberEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Log extends BaseMemberEntity {
    @Id @GeneratedValue
    private Long seq;
    private List<String> to; // 받는쪽 이메일
    private List<String> cc; // 참조
    private List<String> bcc; // 숨은참조
    private String subject; // 메일 제목
    private String content; // 메일 내용
    private Map<String, Object> data;
    private List<MultipartFile> files;
    private LocalDateTime verificationTime; // 인증 완료 시간 (인증되었을 경우)

    @Enumerated(EnumType.STRING)
    private AuthStatus status; // 인증 상태 (요청, 인증, 만료, 실패)
}