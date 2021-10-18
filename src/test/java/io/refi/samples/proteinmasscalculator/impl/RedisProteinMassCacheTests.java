package io.refi.samples.proteinmasscalculator.impl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(PER_CLASS)
public class RedisProteinMassCacheTests {
    
    @Autowired
    private RedisProteinMassCache cache;

    @BeforeAll
	public void setup() {
		cache.clear();
	}

    @Test
	public void allInOne() {
        String testString1 = "A";
        String testString2 = "C";

        assertFalse(cache.containsProtein(testString1));
        assertEquals(cache.getEntryCount(), 0);

        cache.insertProteinMass(testString1, 71.03711);

        assertTrue(cache.containsProtein(testString1));
        assertEquals(cache.getEntryCount(), 1);

        Map<String, Double> entries = cache.getEntries();

        assertTrue(entries.containsKey(testString1));

        cache.insertProteinMass(testString2, 103.00919);

        assertTrue(cache.containsProtein(testString2));
        assertEquals(cache.getEntryCount(), 2);

        cache.removeProtein(testString1);

        assertFalse(cache.containsProtein(testString1));
        assertEquals(cache.getEntryCount(), 1);

        cache.clear();

        assertFalse(cache.containsProtein(testString2));
        assertEquals(cache.getEntryCount(), 0);
    }

}
