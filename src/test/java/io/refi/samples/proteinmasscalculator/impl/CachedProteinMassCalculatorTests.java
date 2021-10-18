package io.refi.samples.proteinmasscalculator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.refi.samples.proteinmasscalculator.models.*;


@SpringBootTest
@ActiveProfiles("test")
class CachedProteinMassCalculatorApplicationTests {

	@Autowired
    private CachedProteinMassCalculator calc;

	@Test
	public void calculateMass_cachedResults() {

        String testString ="CACHED";
 
        ProteinMassRequest req = new ProteinMassRequest(testString);

        ProteinMassResponse resp = calc.calculateMass(req);

        assertFalse(resp.isError);

        assertEquals(calc.cacheMisses.count(), 1.0);
        assertEquals(calc.cacheHits.count(), 0.0);

        calc.calculateMass(req);

        assertEquals(calc.cacheMisses.count(), 1.0);
        assertEquals(calc.cacheHits.count(), 1.0);
	}

}
