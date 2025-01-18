package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO {

    private int id;
    private String descricao;
    private int danificada;
    private int encomendaId;
    private List<ProdutoDTO> produtos;

    // Construtores
    public VolumeDTO() {
        this.produtos=new LinkedList<>();
    }

    public VolumeDTO(int id, String descricao, int danificada, int encomendaId) {
        this.id = id;
        this.descricao = descricao;
        this.danificada = danificada;
        this.encomendaId = encomendaId;
        this.produtos = new LinkedList<>();
    }

    public static VolumeDTO from(Volume volume) {
        return new VolumeDTO(
                volume.getId(),
                volume.getDescricao(),
                volume.getDanificada(),
                volume.getEncomenda().getId()
        );
    }

    public static List<VolumeDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }

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

    public int getDanificada() {
        return danificada;
    }

    public void setDanificada(int danificada) {
        this.danificada = danificada;
    }

    public int getEncomendaId() {
        return encomendaId;
    }

    public void setEncomendaId(int encomendaId) {
        this.encomendaId = encomendaId;
    }

    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }
}
