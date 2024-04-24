package com.vgt.ip.config;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
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

//    @Bean
//    ReactiveRedisOperations<String, IPLocationMongoEntity> ipLocationOperations(ReactiveRedisConnectionFactory factory) {
//        Jackson2JsonRedisSerializer<IPLocationMongoEntity> serializer = new Jackson2JsonRedisSerializer<>(IPLocationMongoEntity.class);
//        RedisSerializationContext.RedisSerializationContextBuilder<String, IPLocationMongoEntity> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
//        RedisSerializationContext<String, IPLocationMongoEntity> context = builder.value(serializer).build();
//
//        return new ReactiveRedisTemplate<>(factory, context);
//    }
    @Bean
    ReactiveRedisOperations<String, IPLocationMongoEntity> ipLocationOperations(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<IPLocationMongoEntity> ipLocationMongoEntityJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(IPLocationMongoEntity.class);

        RedisSerializationContext<String, IPLocationMongoEntity> context = RedisSerializationContext.<String, IPLocationMongoEntity>newSerializationContext()
                .key(stringRedisSerializer)
                .value(ipLocationMongoEntityJackson2JsonRedisSerializer)
                .hashKey(stringRedisSerializer)
                .hashValue(ipLocationMongoEntityJackson2JsonRedisSerializer)
                .build();

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
