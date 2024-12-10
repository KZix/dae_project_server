package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;

@Entity
public class SensorAceleracao extends Sensor {
    private int impactoCount = 0; // Counter for detected impacts

    // Method to detect an impact based on acceleration
    public boolean detectarImpacto(float aceleracao) {
        if (aceleracao > 9.8) { // Example threshold for impact detection
            impactoCount++;
            return true;
        }
        return false;
    }

    // Method to get the total number of impacts detected
    public int obterImpactos() {
        return impactoCount;
    }

    // Getters and Setters
    public int getImpactoCount() {
        return impactoCount;
    }

    public void setImpactCount(int impactoCount) {
        this.impactoCount = impactoCount;
    }
}

