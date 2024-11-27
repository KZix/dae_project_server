package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class SensorPosicao extends Sensor {
    private float intervalo;

    // Method to define the position reading interval
    public void definirIntervalo(float intervalo) {
        this.intervalo = intervalo;
    }

    // Method to retrieve the current position (mock implementation)
    public String obterPosicaoAtual() {
        return "Latitude: 40.748817, Longitude: -73.985428"; // Example coordinates
    }

    // Getters and Setters
    public float getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(float intervalo) {
        this.intervalo = intervalo;
    }
}

