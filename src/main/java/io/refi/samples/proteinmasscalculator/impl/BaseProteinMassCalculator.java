package io.refi.samples.proteinmasscalculator.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.refi.samples.proteinmasscalculator.interfaces.MonoistopicMassTable;
import io.refi.samples.proteinmasscalculator.interfaces.ProteinMassCalculator;
import io.refi.samples.proteinmasscalculator.models.ProteinMassRequest;
import io.refi.samples.proteinmasscalculator.models.ProteinMassResponse;

@Service
/**
 * 
 */
public class BaseProteinMassCalculator implements ProteinMassCalculator {
    
    /**
     * Table used to look up amino acid weights for calculation
     */
    public final MonoistopicMassTable massTable;

    /**
     * Registry for metrics
     */
    public final MeterRegistry meterRegistry;

    /**
     * Timer metric tracking calculation times
     */
    public final LongTaskTimer calculationTimes;
    
    /**
     * Counter metric tracking the number of invalid requests.
     */
    public final Counter errorCount;

    /**
     * Logger
     */
    protected Logger logger;

    @Autowired
    /**
     * 
     * @param massTable
     * @param meterRegistry
     */
    public BaseProteinMassCalculator(MonoistopicMassTable massTable, MeterRegistry meterRegistry) {
        this.massTable = massTable;
        this.meterRegistry = meterRegistry;

        this.calculationTimes = LongTaskTimer.builder("CALCULATION_TIMES").register(this.meterRegistry);
        this.errorCount = meterRegistry.counter("ERROR_COUNT");

        this.logger = LoggerFactory.getLogger(BaseProteinMassCalculator.class);
    }

    /**
     * 
     */
    public ProteinMassResponse calculateMass(ProteinMassRequest request) {
        MDC.put("pmc.request.id", request.id.toString());
        this.logger.info("Calculating mass for protein: {}", request.proteinString);
        
        ProteinMassResponse resp;
        HashMap<Character, Integer> proteinSymbols = new HashMap<Character, Integer>();
        String normProteinStr = this.normalizeProteinString(request.proteinString);

        for (Character sym : normProteinStr.toCharArray()) {
            if (!proteinSymbols.containsKey(sym)){
                proteinSymbols.put(sym, 0);
            }

            proteinSymbols.put(sym, proteinSymbols.get(sym) + 1);
        }

        Set<Character> invalidSymbols = this.detectInvalidSymbols(proteinSymbols.keySet());

        // Received an invalid protein string. Return an error result.
        if (invalidSymbols.size() > 0) {
            String errStr = String.format("Invalid symbol(s) detected: %s", invalidSymbols.toString());
            this.logger.warn(errStr);

            // Increment the ERROR_COUNT metric
            this.errorCount.increment();

            resp = new ProteinMassResponse(
                request.id,
                request.proteinString,
                errStr
            );
        }
        else {
            // Start CALCULATION_TIME metric timer.
            LongTaskTimer.Sample curSample = this.calculationTimes.start();

            // All characters can now be assumed valid.
            double proteinMass = 0.0;
            for (Map.Entry<Character, Integer> symEntry : proteinSymbols.entrySet()) {
                Character sym = symEntry.getKey();
                Integer symCount = symEntry.getValue();

                proteinMass += this.massTable.getAminoAcidMass(sym) * symCount;
            }

            // Stop timer
            long calcTime = curSample.stop();

            this.logger.info("Calculated mass of {} in {}s", proteinMass, calcTime);
            
            resp = new ProteinMassResponse(
                request.id,
                request.proteinString,
                proteinMass
            );
        }

        MDC.clear();

        return resp;
    }

    /**
     * 
     * @param proteinString
     * @return
     */
    private String normalizeProteinString(String proteinString) {
        return proteinString.toUpperCase();
    }

    /**
     * 
     * @param symbols
     * @return
     */
    private Set<Character> detectInvalidSymbols(Set<Character> symbols) {
        Set<Character> invalidSymbols = new HashSet<Character>();

        for (Character sym : symbols) {
            if (!this.massTable.containsAminoAcid(sym)){
                invalidSymbols.add(sym);
            }
        }

        return invalidSymbols;
    }

}