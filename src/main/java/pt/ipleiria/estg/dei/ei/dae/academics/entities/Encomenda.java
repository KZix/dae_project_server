package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "encomendas")
public class Encomenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int cliente;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao = new Date();

    @Column(nullable = false)
    private int estado;

    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Volume> volumes;

    // Constructors
    public Encomenda() {
    }

    public Encomenda(Date dataCriacao, List<Volume> volumes) {
        this.dataCriacao = dataCriacao;
        this.volumes = volumes;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }
}
