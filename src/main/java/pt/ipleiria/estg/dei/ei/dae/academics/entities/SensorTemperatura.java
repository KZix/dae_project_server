package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class SensorTemperatura extends Sensor {
    private float intervalo;

    // Getters and setters

    public float getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(float intervalo) {
        this.intervalo = intervalo;
    }
}

