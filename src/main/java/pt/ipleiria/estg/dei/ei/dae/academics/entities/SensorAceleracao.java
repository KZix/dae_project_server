package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;

@Entity
public class SensorAceleracao extends Sensor {
    private int impactoCount = 0; // Counter for detected impacts


    public boolean detectarImpacto(float aceleracao) {
        float limiarImpacto = 9.8f; // Threshold for impact detection (example: Earth's gravity)

        if (aceleracao > limiarImpacto) {
            impactoCount++;
            return true; // Impact detected
        }
        return false; // No impact detected
    }


    // Getters and Setters
    public int getImpactoCount() {
        return impactoCount;
    }

    public void setImpactoCount(int impactoCount) {
        this.impactoCount = impactoCount;
    }
}
