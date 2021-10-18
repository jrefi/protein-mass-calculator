package io.refi.samples.proteinmasscalculator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class StaticMonoistopicMassTableTests {

	@Autowired
    private StaticMonoistopicMassTable massTable;

	@Test
	public void getEntryCount_correctSize() {

        assertEquals(massTable.getEntryCount(), 20);
    }

}
