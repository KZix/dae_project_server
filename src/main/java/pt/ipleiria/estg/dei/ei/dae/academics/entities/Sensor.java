package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "sensores")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int volume;
    private boolean estado;
    private float valor;

    @Column(name = "ultima_leitura")
    private Date ultimaLeitura;

    private String name;


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

