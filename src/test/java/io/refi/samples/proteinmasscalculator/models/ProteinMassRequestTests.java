package io.refi.samples.proteinmasscalculator.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
class ProteinMassRequestTests {
	@Test
	public void constructor() {

        ProteinMassRequest req = new ProteinMassRequest("AAA");

        assertEquals(req.proteinString, "AAA");
        assertFalse((req.id == null));
    }

}