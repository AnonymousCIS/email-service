package org.anonymous.global.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash(timeToLive=300) // 5분간 값 유지 사용자마다 확인해야 하기 때문에 redis사용
public class CodeValue<T> implements Serializable {

    @Id
    private String code; // 기본키
    private T value; // 제네릭
}
