package io.refi.samples.proteinmasscalculator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.refi.samples.proteinmasscalculator.models.*;


@SpringBootTest
@ActiveProfiles("test")
class BaseProteinMassCalculatorApplicationTests {

	@Autowired
    @Qualifier("baseProteinMassCalculator")
    private BaseProteinMassCalculator calc;


	@Test
	public void calculateMass_validStrings() {
		Map<String, Double> testPairs = new HashMap<String, Double>();
        
        testPairs.put("", 0.0);
        testPairs.put("SKADYEK", 821.39192);
        testPairs.put("smalgic", 675.3084);

        for (Map.Entry<String, Double> testEntry : testPairs.entrySet()) {
            String testString = testEntry.getKey();
            Double testMass = testEntry.getValue();

            ProteinMassResponse resp = calc.calculateMass(
                new ProteinMassRequest(testString)
            );

            assertFalse(resp.isError);
            assertEquals(resp.proteinMass, testMass);
        }
	}


	@Test
	public void calculateMass_invalidStrings() {
		String testProtein = "XZ";

        ProteinMassResponse resp = calc.calculateMass(new ProteinMassRequest(
            testProtein
        ));

        assertTrue(resp.isError);
        assertEquals(resp.proteinMass, -1.0);
        assertEquals(resp.errorString, "Invalid symbol(s) detected: [X, Z]");

        assertEquals(calc.errorCount.count(), 1.0);
	}

}
