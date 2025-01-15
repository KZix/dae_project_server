package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import java.util.Date;
import java.util.List;

public class EncomendaDTO {

    private int id;
    private int cliente;
    private Date dataCriacao;
    private int estado;
    private List<VolumeDTO> volumes;

    // Construtores
    public EncomendaDTO() {
    }

    public EncomendaDTO(int id, int cliente, Date dataCriacao, int estado, List<VolumeDTO> volumes) {
        this.id = id;
        this.cliente = cliente;
        this.dataCriacao = dataCriacao;
        this.estado = estado;
        this.volumes = volumes;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
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

    public List<VolumeDTO> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeDTO> volumes) {
        this.volumes = volumes;
    }
}