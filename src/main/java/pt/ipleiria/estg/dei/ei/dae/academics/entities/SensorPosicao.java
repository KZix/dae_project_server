package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class SensorPosicao extends Sensor {
    private float posicao;

    // Method to define the position reading interval
    public void definirPosicao(float posicao) {
        this.posicao = posicao;
    }

    // Method to retrieve the current position (mock implementation)
    public String obterPosicaoAtual() {
        return "Latitude: 40.748817, Longitude: -73.985428"; // Example coordinates
    }

    // Getters and Setters
    public float getPosicao() {
        return posicao;
    }

    public void setPosicao(float posicao) {
        this.posicao = posicao;
    }


}

