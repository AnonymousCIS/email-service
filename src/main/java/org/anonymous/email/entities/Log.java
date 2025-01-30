package org.anonymous.email.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.email.constants.AuthStatus;
import org.anonymous.global.entities.BaseEntity;
import org.anonymous.global.entities.BaseMemberEntity;
import org.anonymous.member.contants.Authority;


import java.time.LocalDateTime;


@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Log extends BaseMemberEntity {
    @Id @GeneratedValue
    private Long seq;
    @Column(name = "_TO")
    private String to; // 받는쪽 이메일

    private LocalDateTime requestTime; // 요청 시간.

    @Enumerated(EnumType.STRING)
    @Column(name = "_STATUS")
    private AuthStatus status; // 인증 상태 (요청, 인증, 만료, 실패)
    private LocalDateTime verificationTime;

}