package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDTO {
    private int id;
    private String nome;
    private float preco;
    private int tipoProduto;

    public ProdutoDTO() {

    }

    public ProdutoDTO(int id, String nome, float preco, int tipoProduto) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tipoProduto = tipoProduto;
    }

    public static ProdutoDTO from(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getTipoProduto().getId()
        );
    }

    public static List<ProdutoDTO> from(List<Produto> produtos) {
        return produtos.stream().map(ProdutoDTO::from).collect(Collectors.toList());
    }
}
