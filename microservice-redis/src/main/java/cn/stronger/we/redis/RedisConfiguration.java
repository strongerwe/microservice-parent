package cn.stronger.we.redis;

import cn.stronger.we.redis.lock.CusRedissonLockAspect;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class Redis启动配置
 * @department 平台研发部
 * @date 2024-09-20 17:21
 */
@Slf4j
@Configuration(value = "RedisConfiguration")
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public MatchRedisTemplate matchRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        log.info(">>>>>>>> MatchRedisTemplate [Redis操作工具类] Bean Create Success!");
        return new MatchRedisTemplate(stringRedisTemplate);
    }

    @Bean
    public CusRedissonLockAspect cusRedissonLockAspect(RedissonClient redissonClient) {
        log.info(">>>>>>>> @CusRedissonLock [分布式锁注解] Running Success!");
        return new CusRedissonLockAspect(redissonClient);
    }
}
