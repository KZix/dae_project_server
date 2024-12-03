package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Volume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descricao;

    @ManyToMany
    private List<Produto> produtos;

    // Constructors
    public Volume() {
    }

    public Volume(String descricao, List<Produto> produtos) {
        this.descricao = descricao;
        this.produtos = produtos;
    }

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
}
