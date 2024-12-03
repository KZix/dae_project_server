package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class LeituraSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Sensor sensor;

    private float valor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    // Constructors
    public LeituraSensor() {}

    public LeituraSensor(Sensor sensor, float valor, Date timestamp) {
        this.sensor = sensor;
        this.valor = valor;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
