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

    // Construtores
    public EncomendaDTO() {
    }

    public EncomendaDTO(int id, String clienteUsername, Date dataCriacao, int estado) {
        this.id = id;
        this.clienteUsername = clienteUsername;
        this.dataCriacao = dataCriacao;
        this.estado = estado;

    }

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

    public static EncomendaDTO from(Encomenda encomenda) {
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getClienteUsername(),
                encomenda.getDataCriacao(),
                encomenda.getEstado()
        );
    }

    public static List<EncomendaDTO> from(List<Encomenda> encomendas) {
        return encomendas.stream().map(EncomendaDTO::from).collect(Collectors.toList());
    }
}