package org.anonymous.email.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.global.entities.BaseMemberEntity;


@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(indexes = {
        @Index(name = "idx_Log_created_at", columnList = "createdAt DESC")
})
public class EmailLog extends BaseMemberEntity {
    @Id @GeneratedValue
    private Long seq;
    @Column(name = "_TO", length=100)
    private String to; // 받는쪽 이메일

    private String subject; // 제목

    @Lob
    private String content; // 내용
}