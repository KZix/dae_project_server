package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;

@Entity
public class SensorTemperatura extends Sensor {
    private float threshold; // Threshold value for comparison
    private int belowThresholdCount = 0; // Counter to track how many times the value is below the threshold

    // Getter and Setter for threshold
    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    // Getter and Setter for belowThresholdCount
    public int getBelowThresholdCount() {
        return belowThresholdCount;
    }

    public void setBelowThresholdCount(int belowThresholdCount) {
        this.belowThresholdCount = belowThresholdCount;
    }

    // Method to detect if the temperature is below the threshold (threshold)
    public boolean checkTemperatureBelowThreshold(float temperature) {
        if (temperature < threshold) {
            belowThresholdCount++;  // Increment the counter if the value is below the threshold
            return true; // Indicate that the temperature is below the threshold
        }
        return false; // Indicate that the temperature is not below the threshold
    }
}


