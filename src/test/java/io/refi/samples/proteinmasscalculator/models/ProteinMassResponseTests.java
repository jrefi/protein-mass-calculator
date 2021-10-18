package io.refi.samples.proteinmasscalculator.models;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProteinMassResponseTests {
	@Test
	public void constructor_goodResponse() {
        UUID reqID = UUID.randomUUID();
        ProteinMassResponse resp = new ProteinMassResponse(reqID, "AAA", 213.11133);

        assertEquals(resp.requestId, reqID);
        assertEquals(resp.requestString, "AAA");
        assertEquals(resp.proteinMass, 213.11133);
        assertFalse(resp.isError);

    }

    @Test
	public void constructor_errorResponse() {
        UUID reqID = UUID.randomUUID();
        ProteinMassResponse resp = new ProteinMassResponse(reqID, "AAA", "Error Message");

        assertEquals(resp.requestId, reqID);
        assertEquals(resp.requestString, "AAA");
        assertEquals(resp.proteinMass, -1.0);
        assertTrue(resp.isError);

    }

}