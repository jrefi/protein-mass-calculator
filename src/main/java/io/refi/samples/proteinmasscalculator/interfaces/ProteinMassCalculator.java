package io.refi.samples.proteinmasscalculator.interfaces;

import io.refi.samples.proteinmasscalculator.models.ProteinMassRequest;
import io.refi.samples.proteinmasscalculator.models.ProteinMassResponse;

/**
 * Service which calculates the mass of a given protein string.
 */
public interface ProteinMassCalculator {

    /**
     * 
     * @param request Object detailing the protein whose mass is requested.
     * @return Response object containing the mass if the protein string was valid or an error message if invalid.
     */
    public abstract ProteinMassResponse calculateMass(ProteinMassRequest request);
}