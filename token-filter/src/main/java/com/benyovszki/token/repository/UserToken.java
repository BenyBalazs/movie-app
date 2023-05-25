package com.benyovszki.token.repository;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("Token")
@Builder
@Getter
@Setter
public class UserToken implements Serializable {

    @Id
    private String token;
    private String userId;
    private String username;
    private String role;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private long expiration = 10; // TTL in minutes
}
