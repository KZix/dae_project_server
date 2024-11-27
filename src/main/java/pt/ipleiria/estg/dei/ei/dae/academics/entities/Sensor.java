package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int volume;
    private boolean estado;
    private float valor;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaLeitura;

    // Abstract methods for subclasses

    public void enviarLeitura(float valor) {

    }

    public void ativar(int id) {

    }

    public void desativar(int id) {

    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

