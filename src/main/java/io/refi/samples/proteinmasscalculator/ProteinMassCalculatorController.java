package io.refi.samples.proteinmasscalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.refi.samples.proteinmasscalculator.interfaces.ProteinMassCalculator;
import io.refi.samples.proteinmasscalculator.models.ProteinMassRequest;
import io.refi.samples.proteinmasscalculator.models.ProteinMassResponse;

@RestController
public class ProteinMassCalculatorController {

    private ProteinMassCalculator calc;

    @Autowired
    public ProteinMassCalculatorController(ProteinMassCalculator calc) {
        this.calc = calc;
    }

    @PostMapping("/calculateProteinMass")
    public ProteinMassResponse calculateProteinMass(@RequestBody ProteinMassRequest req) {
        return this.calc.calculateMass(req);
    }
}