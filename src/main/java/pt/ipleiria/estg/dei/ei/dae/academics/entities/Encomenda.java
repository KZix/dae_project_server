package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEncomendas",
                query = "SELECT s FROM Encomenda s ORDER BY s.id" // JPQL
        )
})
@Table(name = "encomendas")
public class Encomenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "cliente_username")
    private String clienteUsername;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao = new Date();

    @NotNull
    private int estado;

    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Volume> volumes;

    // Construtores
    public Encomenda() {
        this.volumes = new LinkedList<>();
    }

    public Encomenda(String clienteUsername, Date dataCriacao, int estado) {
        this.clienteUsername = clienteUsername;
        this.dataCriacao = dataCriacao;
        this.estado = estado;
        this.volumes = new LinkedList<>();
    }
    // Getters e Setters
    public void addVolume(Volume volume) {this.volumes.add(volume);}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getClienteUsername() {
        return clienteUsername;
    }

    public void setClienteUsername(String clienteUsername) {
        this.clienteUsername = clienteUsername;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes =

                volumes;
    }
}