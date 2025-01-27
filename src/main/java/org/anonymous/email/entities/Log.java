package org.anonymous.email.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.email.constants.AuthStatus;
import org.anonymous.global.entities.BaseEntity;
import org.anonymous.global.entities.BaseMemberEntity;


import java.time.LocalDateTime;


@Data
@Entity
public class Log extends BaseMemberEntity {
    @Id @GeneratedValue
    private Long seq;
    @Column(name = "_TO")
    private String to; // 받는쪽 이메일

    private LocalDateTime requestTime; // 요청 시간.

    private LocalDateTime verificationTime; // 인증 완료 시간 (인증되었을 경우)

    @Enumerated(EnumType.STRING)
    @Column(name = "_STATUS")
    private AuthStatus status; // 인증 상태 (요청, 인증, 만료, 실패)
}