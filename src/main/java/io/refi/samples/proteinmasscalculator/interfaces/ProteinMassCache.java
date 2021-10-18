package io.refi.samples.proteinmasscalculator.interfaces;

import java.util.Map;

/**
 * Cache containing protin string/mass entries.
 */
public interface ProteinMassCache {
    
    /**
     * Check if the cache contains a particular protein string
     * @param proteinString Protein string to check
     * @return True if protein string is in cache. False otherwise.
     */
    public abstract boolean containsProtein(String proteinString);

    /**
     * Get the mass of a protein string from the cache.
     * @param proteinString  The protein string whose mass is to be retrieved.
     * @return The mass of the protein string from the cache.
     */
    public abstract Double getProteinMass(String proteinString);

    /**
     * Add a new protein string with its mass to the cache.
     * @param proteinString The protein string to add
     * @param proteinMass The mass of the added protein string
     */
    public abstract void insertProteinMass(String proteinString, Double proteinMass);

    /**
     * Get a map of all protein string/mass entries in the cache
     * @return A map of protein string/mass pairs
     */
    public abstract Map<String, Double> getEntries();

    /**
     * Get the count of entries in the cache
     * @return Number of entries in the cache
     */
    public abstract long getEntryCount();

    /**
     * Remove a protein string entry from the cache
     * @param proteinString The protein string to remove
     */
    public abstract void removeProtein(String proteinString);

    /**
     * Clear all entries from the cache
     */
    public abstract void clear();

}