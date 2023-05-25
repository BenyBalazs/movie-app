package com.benyovszki.token.jedis;

import java.util.Objects;

import jakarta.annotation.Nullable;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@NoArgsConstructor
@EnableRedisRepositories
public class AuthJedisConfig {

    @Value("${redis.auth.host:localhost}")
    private String redisHost;

    @Value("${redis.auth.port:6379}")
    private int redisPort;

    @Value("${redis.auth.password:#{null}}")
    @Nullable
    private String redisPassword;

    @Value("${redis.auth.db: 0}")
    private int redisDb;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);
        configuration.setDatabase(redisDb);
        if (Objects.nonNull(redisPassword)) {
            configuration.setPassword(redisPassword);
        }
        return new JedisConnectionFactory(configuration);

    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
