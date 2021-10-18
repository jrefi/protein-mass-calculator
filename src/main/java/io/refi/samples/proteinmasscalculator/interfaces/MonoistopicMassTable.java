package io.refi.samples.proteinmasscalculator.interfaces;

/**
 * Interface for all MonoistopicMassTable implementations
 */
public interface MonoistopicMassTable {
   
    /**
     * Check if the table contains an entry for a particular amino acid symbol.
     * @param aminoAcidSymbol Amino acid symbol character to check
     * @return True if the table contains the amino acid. False otherwise.
     */
    public abstract boolean containsAminoAcid(Character aminoAcidSymbol);

    /**
     * Get the mass of an amino acid
     * @param aminoAcidSymbol The amino acid symbol whose mass is requested.
     * @return The mass of the requested amino acid
     */
    public abstract double getAminoAcidMass(Character aminoAcidSymbol);
    
    /**
     * Get count of all amino acid/mass entries in the table.
     * @return Number of entries in the table
     */
    public abstract long getEntryCount();

}