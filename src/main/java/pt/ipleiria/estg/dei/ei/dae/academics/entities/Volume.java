package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT s FROM Volume s ORDER BY s.id" // JPQL
        )
})
@Table(name = "volumes")
public class Volume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descricao;
    @NotNull
    private int danificada;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "encomenda_id")
    private Encomenda encomenda;

    @ManyToMany(mappedBy = "volumes")
    private List<Produto> produtos;

    @ManyToOne
    private Sensor assignedSensor;

    // Constructors
    public Volume() {
        this.produtos= new LinkedList<>();
    }

    public Volume(String descricao, int danificada, Encomenda encomenda) {
        this.descricao = descricao;
        this.danificada = danificada;
        this.encomenda = encomenda;
        this.produtos = new LinkedList<>();
    }

    public void addProduto(Produto produto) {this.produtos.add(produto);}
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public int getDanificada() {
        return danificada;
    }

    public void setDanificada(int tipoEmbalagem) {
        this.danificada = tipoEmbalagem;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

    public Sensor getAssignedSensor() {
        return assignedSensor;
    }

    public void setAssignedSensor(Sensor assignedSensor) {
        this.assignedSensor = assignedSensor;
    }
}
