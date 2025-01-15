package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.TipoProduto;

import java.util.List;
import java.util.stream.Collectors;

public class TipoProdutoDTO {
    private int id;
    private String nome;

    public TipoProdutoDTO() {
    }

    public TipoProdutoDTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static TipoProdutoDTO from(TipoProduto tipoProduto) {
        return new TipoProdutoDTO(
                tipoProduto.getId(),
                tipoProduto.getNome()
        );
    }

    public static List<TipoProdutoDTO> from(List<TipoProduto> tipoProdutos) {
        return tipoProdutos.stream().map(TipoProdutoDTO::from).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
