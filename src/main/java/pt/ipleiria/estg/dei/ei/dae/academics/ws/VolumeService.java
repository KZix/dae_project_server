package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("volumes") // Relative URL for this service
@Produces({MediaType.APPLICATION_JSON}) // Specifies the response format as JSON
@Consumes({MediaType.APPLICATION_JSON}) // Specifies the request format as JSON
public class VolumeService {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private VolumeBean volumeBean;

    @GET
    @Path("/")
    public List<VolumeDTO> getAllVolumes() {
        // Recupera todos os volumes e converte para DTO
        List<Volume> volumes = volumeBean.findAll();
        return volumes.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }

    @GET
    @Path("{volumeId}")
    public Response getVolumeById(@PathParam("volumeId") int id) {
        Volume volume = volumeBean.find(id);
        if (volume == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Volume não encontrado").build();
        }
        return Response.ok(VolumeDTO.from(volume)).build();
    }

    @POST
    @Path("/")
    public Response createVolume(VolumeDTO volumeDTO) {
        try {
            // Obter os produtos do banco de dados através dos IDs fornecidos
            List<Produto> produtos = volumeDTO.getProdutos().stream()
                    .map(produtoDTO -> em.find(Produto.class, produtoDTO.getId())) // Encontra cada Produto pelo ID
                    .filter(produto -> produto != null) // Filtra produtos nulos (caso não encontre um Produto com o ID fornecido)
                    .collect(Collectors.toList());

            // Obter a encomenda do banco de dados pelo ID fornecido
            Encomenda encomenda = volumeDTO.getEncomenda() != null ?
                    em.find(Encomenda.class, volumeDTO.getEncomenda().getId()) : null;

            // Verificar se a encomenda e os produtos são válidos
            if (encomenda == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Encomenda não encontrada").build();
            }

            // Criar o volume com os dados fornecidos
            Volume volume = volumeBean.create(
                    volumeDTO.getDescricao(),
                    produtos,
                    encomenda
            );

            return Response.status(Response.Status.CREATED).entity(VolumeDTO.from(volume)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{volumeId}")
    public Response updateVolume(@PathParam("volumeId") int id, VolumeDTO volumeDTO) {
        try {
            // Obter os produtos do banco de dados através dos IDs fornecidos
            List<Produto> produtos = volumeDTO.getProdutos().stream()
                    .map(produtoDTO -> em.find(Produto.class, produtoDTO.getId())) // Encontra cada Produto pelo ID
                    .filter(produto -> produto != null) // Filtra produtos nulos (caso não encontre um Produto com o ID fornecido)
                    .collect(Collectors.toList());

            // Atualizar o volume com os dados fornecidos
            volumeBean.update(id, volumeDTO.getDescricao(), produtos);

            // Encontrar o volume atualizado
            Volume updatedVolume = volumeBean.find(id);
            if (updatedVolume == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Volume não encontrado").build();
            }

            // Retornar a resposta com o volume atualizado
            return Response.ok(VolumeDTO.from(updatedVolume)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{volumeId}")
    public Response deleteVolume(@PathParam("volumeId") int id) {
        Volume volume = volumeBean.find(id);
        if (volume == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Volume não encontrado").build();
        }
        volumeBean.delete(id);
        return Response.noContent().build();
    }

}