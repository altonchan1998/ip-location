package com.vgt.ip.config;

import com.vgt.ip.model.entity.IPLocation;
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
    ReactiveRedisOperations<String, IPLocation> ipLocationOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<IPLocation> serializer = new Jackson2JsonRedisSerializer<>(IPLocation.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, IPLocation> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, IPLocation> context = builder.value(serializer).build();

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
