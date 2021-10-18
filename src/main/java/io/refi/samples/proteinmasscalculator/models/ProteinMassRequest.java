package io.refi.samples.proteinmasscalculator.models;

import java.util.UUID;

/**
 * A request to calculate a protein mass.
*/
public class ProteinMassRequest  {

    /** Unique ID for the request to be used for tracking.
    */
    public UUID id;

    /** A string containing the amino acid symbols that compose the protein.
    */
    public String proteinString;

    /**
     * Empty contstructor required for JSON (de)serialization
     */
    public ProteinMassRequest() {}

    /**
     * Default constructor. ID will be randomly generated.
     * @param proteinString The protein string whose mass will be calculated
     */
    public ProteinMassRequest(String proteinString)  {
        this.id = UUID.randomUUID();
        this.proteinString = proteinString;
    }

}