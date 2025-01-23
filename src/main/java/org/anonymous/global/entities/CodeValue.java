package org.anonymous.global.entities;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;

@Data
@RedisHash(timeToLive = 300)
public class CodeValue {
    @Id
    private String code;
    private Object value;
}
