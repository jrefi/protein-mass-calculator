package io.refi.samples.proteinmasscalculator.impl;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


import io.refi.samples.proteinmasscalculator.interfaces.MonoistopicMassTable;
import io.refi.samples.proteinmasscalculator.interfaces.ProteinMassCache;

import io.refi.samples.proteinmasscalculator.models.ProteinMassRequest;
import io.refi.samples.proteinmasscalculator.models.ProteinMassResponse;

@Primary
@Service
public class CachedProteinMassCalculator extends BaseProteinMassCalculator {
    
    private final ProteinMassCache cache;
    public final Counter cacheHits;
    public final Counter cacheMisses;

    @Autowired
    public CachedProteinMassCalculator(MonoistopicMassTable massTable, MeterRegistry meterRegistry, ProteinMassCache cache) {
        super(massTable, meterRegistry);
        this.cache = cache;

        this.cacheHits = this.meterRegistry.counter("CACHE_HITS");
        this.cacheMisses = this.meterRegistry.counter("CACHE_MISSES");
        this.logger = LoggerFactory.getLogger(CachedProteinMassCalculator.class);
    }
    
    public ProteinMassResponse calculateMass(ProteinMassRequest request) {
        
        // Check for cached mass for this request protein string.
        if (this.cache.containsProtein(request.proteinString)) {
            this.logger.info("Protein {} found in cache.", request.proteinString);
            this.cacheHits.increment();

            return new ProteinMassResponse(
                request.id,
                request.proteinString,
                this.cache.getProteinMass(request.proteinString)
            );
        }

        this.cacheMisses.increment();

        // Calculate mass using base class method.
        ProteinMassResponse resp = super.calculateMass(request);

        if (!resp.isError) {
            this.logger.info("Adding protein {} with mass {} to cache.", request.proteinString, resp.proteinMass);

            // Insert new result mass into cache.
            this.cache.insertProteinMass(
                request.proteinString,
                resp.proteinMass
            );
        }

        return resp;
    }
}