package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllTipoProdutos",
                query = "SELECT s FROM TipoProduto s ORDER BY s.nome" // JPQL
        )
})
public class TipoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

    // Constructors
    public TipoProduto() {
    }

    public TipoProduto(String nome) {
        this.nome = nome;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
