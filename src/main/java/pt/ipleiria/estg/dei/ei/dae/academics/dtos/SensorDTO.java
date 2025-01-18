package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import java.util.Date;

public class SensorDTO {
    private int id;
    private String name;
    private int volume;
    private boolean estado;
    private float valor;
    private Date ultimaLeitura;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {}

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getUltimaLeitura() {
        return ultimaLeitura;
    }

    public void setUltimaLeitura(Date ultimaLeitura) {
        this.ultimaLeitura = ultimaLeitura;
    }
}

