package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import java.util.List;

public class VolumeDTO {

    private int id;
    private String descricao;
    private int tipoEmbalagem;
    private EncomendaDTO encomenda;
    //private List<ProdutoDTO> produtos;

    // Construtores
    public VolumeDTO() {
    }

    public VolumeDTO(int id, String descricao, int tipoEmbalagem, EncomendaDTO encomenda /*, List<ProdutoDTO> produtos*/) {
        this.id = id;
        this.descricao = descricao;
        this.tipoEmbalagem = tipoEmbalagem;
        this.encomenda = encomenda;
        //this.produtos = produtos;
    }

    // Getters e Setters
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

    public int getTipoEmbalagem() {
        return tipoEmbalagem;
    }

    public void setTipoEmbalagem(int tipoEmbalagem) {
        this.tipoEmbalagem = tipoEmbalagem;
    }

    public EncomendaDTO getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(EncomendaDTO encomenda) {
        this.encomenda = encomenda;
    }
/*
    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }*/
}