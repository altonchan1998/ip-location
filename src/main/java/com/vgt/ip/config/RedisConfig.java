package com.vgt.ip.config;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationRedisEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    ReactiveRedisOperations<String, IPLocationRedisEntity> ipLocationOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<IPLocationRedisEntity> serializer = new Jackson2JsonRedisSerializer<>(IPLocationRedisEntity.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, IPLocationRedisEntity> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, IPLocationRedisEntity> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    ReactiveRedisOperations<String, Long> ipLocationVersionOperations(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericToStringSerializer<Long> longGenericToStringSerializer = new GenericToStringSerializer<>(Long.class);

        RedisSerializationContext<String, Long> context = RedisSerializationContext.<String, Long>newSerializationContext()
                .key(stringRedisSerializer)
                .value(longGenericToStringSerializer)
                .hashKey(stringRedisSerializer)
                .hashValue(longGenericToStringSerializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
