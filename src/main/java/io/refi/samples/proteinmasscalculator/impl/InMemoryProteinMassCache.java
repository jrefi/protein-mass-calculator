package io.refi.samples.proteinmasscalculator.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import io.refi.samples.proteinmasscalculator.interfaces.ProteinMassCache;

@Repository
public class InMemoryProteinMassCache implements ProteinMassCache {

    private Map<String, Double> cacheMap;

    private Logger logger;

    public InMemoryProteinMassCache() {
        this.cacheMap = new HashMap<String, Double>();
        this.logger = LoggerFactory.getLogger(InMemoryProteinMassCache.class);
    }
    
    @Override
    public boolean containsProtein(String proteinString) {
        this.logger.info("Checking cache for protein {}", proteinString);
        return this.cacheMap.containsKey(proteinString);
    }

    @Override
    public Double getProteinMass(String proteinString) {
        this.logger.info("Getting mass of protein {} from cache", proteinString);
        return this.cacheMap.get(proteinString);
    }

    @Override
    public void insertProteinMass(String proteinString, Double proteinMass) {
        this.logger.info("Inserting protein {} with mass {} into cache", proteinString, proteinMass);
        this.cacheMap.put(proteinString, proteinMass);
    }

    @Override
    public Map<String, Double> getEntries() {
        return this.cacheMap;
    }

    @Override
    public long getEntryCount() {
        return (long)this.cacheMap.size();
    }

    @Override
    public void removeProtein(String proteinString) {
        this.cacheMap.remove(proteinString);
        
    }

    @Override
    public void clear() {
        this.cacheMap.clear();
    }
}