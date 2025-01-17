package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

@Path("encomendas")
@Produces("application/json")
@Consumes("application/json")
public class EncomendaService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EncomendaBean encomendaBean;

    // Criar uma encomenda
    @POST
    @Path("/")
    public Response createEncomenda(EncomendaDTO encomendaDTO) {
        try {
            // Criar a encomenda com os dados fornecidos (sem volumes)
            Encomenda encomenda = encomendaBean.create(
                    encomendaDTO.getClienteUsername(),
                    encomendaDTO.getDataCriacao(),
                    encomendaDTO.getEstado()
            );
            return Response.status(Response.Status.CREATED).entity(EncomendaDTO.from(encomenda)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Buscar todas as encomendas
    @GET
    @Path("/")
    public List<EncomendaDTO> getAllEncomendas() {
        List<Encomenda> encomendas = encomendaBean.findAll();
        return encomendas.stream()
                .map(EncomendaDTO::from)
                .collect(Collectors.toList());
    }

    // Buscar uma encomenda por ID
    @GET
    @Path("{encomendaId}")
    public Response getEncomendaById(@PathParam("encomendaId") int id) {
        Encomenda encomenda = encomendaBean.find(id);
        if (encomenda == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Encomenda não encontrada").build();
        }
        return Response.ok(EncomendaDTO.from(encomenda)).build();
    }

    // Adicionar volumes à encomenda
    @POST
    @Path("{encomendaId}/volumes")
    public Response addVolumesToEncomenda(@PathParam("encomendaId") int encomendaId, List<Integer> volumeIds) {
        try {
            Encomenda encomenda = encomendaBean.find(encomendaId);
            if (encomenda == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Encomenda não encontrada").build();
            }

            List<Volume> volumes = volumeIds.stream()
                    .map(id -> em.find(Volume.class, id))
                    .filter(volume -> volume != null) // Filtra volumes nulos
                    .collect(Collectors.toList());

            if (volumes.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Nenhum volume válido fornecido").build();
            }

            encomendaBean.addVolumes(encomenda, volumes);

            return Response.ok(EncomendaDTO.from(encomenda)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}