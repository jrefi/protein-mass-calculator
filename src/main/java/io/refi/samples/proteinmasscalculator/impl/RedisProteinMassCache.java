package io.refi.samples.proteinmasscalculator.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import io.refi.samples.proteinmasscalculator.interfaces.ProteinMassCache;

@Repository
public class RedisProteinMassCache implements ProteinMassCache {
    private RedisTemplate<String, Double> template;
    private HashOperations<String, String, Double> hashOps;
    private String hashKey;

    private Logger logger;

    @Autowired
    public RedisProteinMassCache(RedisTemplate<String, Double> template) {
        this.template = template;
        this.hashKey = "PMC";
        this.hashOps = this.template.<String, Double>opsForHash();

        this.logger = LoggerFactory.getLogger(RedisProteinMassCache.class);
    }


    @Override
    public boolean containsProtein(String proteinString) {
        this.logger.info("Checking cache for protein {}", proteinString);
        return this.hashOps.hasKey(this.hashKey, proteinString);
    }

    @Override
    public Double getProteinMass(String proteinString) {
        this.logger.info("Getting mass of protein {} from cache", proteinString);
        return this.hashOps.get(this.hashKey, proteinString);
    }

    @Override
    public void insertProteinMass(String proteinString, Double proteinMass) {
        this.logger.info("Inserting protein {} with mass {} into cache", proteinString, proteinMass);
        this.hashOps.put(this.hashKey, proteinString, proteinMass);
        
    }

    @Override
    public Map<String, Double> getEntries() {
        return this.hashOps.entries(this.hashKey);
    }

    @Override
    public long getEntryCount() {
        return this.hashOps.size(this.hashKey);
    }

    @Override
    public void removeProtein(String proteinString) {
        this.hashOps.delete(this.hashKey, proteinString);
        
    }

    @Override
    public void clear() {
        this.template.delete(this.hashKey); 
    }

}
