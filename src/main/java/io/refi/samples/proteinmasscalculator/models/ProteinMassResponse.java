package io.refi.samples.proteinmasscalculator.models;

import java.util.UUID;

/**
 * A response to a request to calculate a protein mass.
*/
public class ProteinMassResponse {

    /**
     * ID of request for tracking purposes.
    */
    public UUID requestId;

    /**
     * Protein string requested for calculation.
    */
    public String requestString;

    /**
     * Resulting mass of input protein string.
    */
    public Double proteinMass;


    /** Whether an error occurred during calculation.
    */
    public boolean isError;


    /** 
     * String describing any errors occurred during calculation.
     * Will be set only when isError == True
    */
    public String errorString;

    /**
     * Empty constructor required for JSON (de)serialization
    */
    public ProteinMassResponse() {}

    /**
     * Constructor for non-error responses.
     * @param requestId ID of request that generated this response
     * @param requestString Protein string requested for calcuation
     * @param proteinMass Resulting mass of protein string
     */
    public ProteinMassResponse(UUID requestId, String requestString, Double proteinMass) {
        this.requestId = requestId;
        this.requestString = requestString;
        this.proteinMass = proteinMass;
        this.isError = false;
        this.errorString = null;
    }

    /**
     * Constructor for error responses. IsError will be set to true.
     * @param requestId ID of request that generated this response
     * @param requestString Protein string requested for calcuation
     * @param errorString String describing the error
     */
    public ProteinMassResponse(UUID requestId, String requestString, String errorString) {
        this.requestId = requestId;
        this.requestString = requestString;
        this.proteinMass = -1.0;
        this.isError = true;
        this.errorString = errorString;
    }

}