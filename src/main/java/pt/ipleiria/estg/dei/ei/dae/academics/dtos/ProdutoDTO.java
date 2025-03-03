package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDTO {
    private int id;
    private String nome;
    private float preco;
    private int tipoProdutoId;


    public ProdutoDTO() {

    }

    public ProdutoDTO(int id, String nome, float preco, int tipoProduto) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tipoProdutoId = tipoProduto;

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

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        this.preco = preco;
    }

    public int getTipoProdutoId() {
        return tipoProdutoId;
    }

    public void setTipoProdutoId(int tipoProdutoId) {
        this.tipoProdutoId = tipoProdutoId;
    }
}
