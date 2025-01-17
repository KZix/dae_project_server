package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Encomenda;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EncomendaDTO {

    private int id;
    private String clienteUsername;
    private Date dataCriacao;
    private int estado;
    private List<VolumeDTO> volumes;

    // Construtores
    public EncomendaDTO() {
    }

    public EncomendaDTO(int id, String clienteUsername, Date dataCriacao, int estado, List<VolumeDTO> volumes) {
        this.id = id;
        this.clienteUsername = clienteUsername;
        this.dataCriacao = dataCriacao;
        this.estado = estado;
        this.volumes = volumes;
    }

    public static EncomendaDTO from(Encomenda encomenda) {
        if (encomenda == null) {
            return null;
        }

        // Converte a lista de volumes para o formato DTO
        List<VolumeDTO> volumesDTO = encomenda.getVolumes().stream()
                .map(VolumeDTO::from)
                .collect(Collectors.toList());

        // Retorna o EncomendaDTO com os valores da entidade Encomenda
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getClienteUsername(),
                encomenda.getDataCriacao(),
                encomenda.getEstado(),
                volumesDTO
        );
    }
    // Getters e Setters
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

    public List<VolumeDTO> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeDTO> volumes) {
        this.volumes = volumes;
    }
}