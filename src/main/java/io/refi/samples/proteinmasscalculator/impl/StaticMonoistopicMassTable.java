package io.refi.samples.proteinmasscalculator.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import io.refi.samples.proteinmasscalculator.interfaces.MonoistopicMassTable;

@Repository
public class StaticMonoistopicMassTable implements MonoistopicMassTable {
   
    private Map<Character, Double> massTable;

    public StaticMonoistopicMassTable() {
        this.massTable = new HashMap<Character, Double>();
        this.massTable.put('A', 71.03711);
        this.massTable.put('C', 103.00919);
        this.massTable.put('D', 115.02694);
        this.massTable.put('E', 129.04259);
        this.massTable.put('F', 147.06841);
        this.massTable.put('G', 57.02146);
        this.massTable.put('H', 137.05891);
        this.massTable.put('I', 113.08406);
        this.massTable.put('K', 128.09496);
        this.massTable.put('L', 113.08406);
        this.massTable.put('M', 131.04049);
        this.massTable.put('N', 114.04293);
        this.massTable.put('P', 97.05276);
        this.massTable.put('Q', 128.05858);
        this.massTable.put('R', 156.10111);
        this.massTable.put('S', 87.03203);
        this.massTable.put('T', 101.04768);
        this.massTable.put('V', 99.06841);
        this.massTable.put('W', 186.07931);
        this.massTable.put('Y', 163.06333);
    }

    @Override
    public boolean containsAminoAcid(Character aminoAcidSymbol) {
        return this.massTable.containsKey(aminoAcidSymbol);
    }

    @Override
    public double getAminoAcidMass(Character aminoAcidSymbol) {
        return this.massTable.get(aminoAcidSymbol);
    }

    @Override
    public long getEntryCount() {
        return (long)this.massTable.size();
    }

    
}