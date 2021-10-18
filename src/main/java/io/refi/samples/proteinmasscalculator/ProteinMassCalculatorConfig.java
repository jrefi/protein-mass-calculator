package io.refi.samples.proteinmasscalculator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.refi.samples.proteinmasscalculator.impl.BaseProteinMassCalculator;
import io.refi.samples.proteinmasscalculator.impl.CachedProteinMassCalculator;
import io.refi.samples.proteinmasscalculator.impl.InMemoryProteinMassCache;
import io.refi.samples.proteinmasscalculator.impl.RedisProteinMassCache;
import io.refi.samples.proteinmasscalculator.impl.StaticMonoistopicMassTable;
import io.refi.samples.proteinmasscalculator.interfaces.MonoistopicMassTable;
import io.refi.samples.proteinmasscalculator.interfaces.ProteinMassCache;
import io.refi.samples.proteinmasscalculator.interfaces.ProteinMassCalculator;


@Configuration
@EnableAspectJAutoProxy
public class ProteinMassCalculatorConfig {
    
    
    // https://stackoverflow.com/questions/48704789/how-to-measure-service-methods-using-spring-boot-2-and-micrometer
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    @ConditionalOnMissingBean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }

    @Value("${pmc.cache.enabled}")
    private Boolean cacheEnabled;

    @Value("${pmc.cache.type}")
    private String cacheType;

    @Value("${pmc.cache.redis.host:redis}")
    private String redisHost;

    @Value("${pmc.cache.redis.port:6379}")
    private int redisPort;

    @Bean
    @ConditionalOnMissingBean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();

        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);

        LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisConfig);

        return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Double> redisTemplate() {
        RedisTemplate<String, Double> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public MonoistopicMassTable massTable() {
        return new StaticMonoistopicMassTable();
    }

    @Bean
    public ProteinMassCache cache() {
        if (cacheType.toLowerCase().equals("redis")) {
            return new RedisProteinMassCache(redisTemplate());
        }
        else if (cacheType.toLowerCase().equals("inmemory")) {
            return new InMemoryProteinMassCache();
        }
        else {
            throw new IllegalArgumentException(String.format("Invalid cache type: %s", cacheType));
        }
    }

    @Bean
    public ProteinMassCalculator calc() {
        if (cacheEnabled) {
            return new CachedProteinMassCalculator(massTable(), meterRegistry(), cache());
        }
        else {
            return new BaseProteinMassCalculator(massTable(), meterRegistry());
        }
    }



}