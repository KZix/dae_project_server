package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("volumes") // Relative URL for this service
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class VolumeService {

    @EJB
    private VolumeBean volumeBean;

    @GET
    @Path("/")
    public List<VolumeDTO> getAllVolumes() {
        return volumeBean.findAll()
                .stream()
                .map(VolumeDTO::from)
                .collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    public Response getVolumeById(@PathParam("id") int id) {
        try {
            Volume volume = volumeBean.findWithProducts(id); // Corrigir "produts" para "products"

            if (volume == null) {
                throw new IllegalArgumentException("Volume not found with ID: " + id);
            }

            // Mapeando os dados do volume
            Map<String, Object> volumeMap = new LinkedHashMap<>();
            volumeMap.put("id", volume.getId());
            volumeMap.put("descricao", volume.getDescricao());
            volumeMap.put("danificada", volume.getDanificada());
            volumeMap.put("encomendaId", volume.getEncomenda().getId());

            // Mapeando os produtos do volume
            List<Map<String, Object>> produtos = volume.getProdutos().stream().map(produto -> {
                Map<String, Object> produtoMap = new LinkedHashMap<>();
                produtoMap.put("id", produto.getId());
                produtoMap.put("nome", produto.getNome());
                produtoMap.put("preco", produto.getPreco());

                Map<String, Object> tipoProdutoMap = new LinkedHashMap<>();
                tipoProdutoMap.put("id", produto.getTipoProduto().getId());
                tipoProdutoMap.put("nome", produto.getTipoProduto().getNome());

                produtoMap.put("tipoProduto", tipoProdutoMap);
                return produtoMap;
            }).collect(Collectors.toList());

            volumeMap.put("produtos", produtos);

            return Response.ok(volumeMap).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/")
    public Response createVolume(VolumeDTO volumeDTO) {
        try {
            Volume volume = volumeBean.create(volumeDTO.getDescricao(), volumeDTO.getDanificada(),volumeDTO.getEncomendaId());
            return Response.status(Response.Status.CREATED).entity(VolumeDTO.from(volume)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response updateVolume(@PathParam("id") int id, VolumeDTO volumeDTO) {
        try {
            // Atualizar o volume usando os IDs dos produtos
            volumeBean.update(id, volumeDTO.getDescricao(), volumeDTO.getDanificada(), volumeDTO.getEncomendaId());

            // Retornar o volume atualizado
            Volume updatedVolume = volumeBean.find(id);
            return Response.ok(VolumeDTO.from(updatedVolume)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @DELETE
    @Path("{id}")
    public Response deleteVolume(@PathParam("id") int id) {
        try {
            volumeBean.delete(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}/removeProdutos")
    public Response deleteProdutosVolume(@PathParam("id") int id, List<Integer> produtoId) {
        try {
            volumeBean.removeProdutosFromVolume(produtoId, id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{id}/produtos")
    public Response addProdutosToVolume(@PathParam("id") int volumeId, List<Integer> produtoIds) {
        try {
            volumeBean.addProdutosToVolume( produtoIds,volumeId);
            return Response.ok("Produtos adicionados com sucesso ao Volume com ID " + volumeId).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{volumeId}/assign-sensor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignSensorToVolume(@PathParam("volumeId") int volumeId, SensorDTO sensorDTO) {
        try {
            volumeBean.assignSensor(volumeId, sensorDTO.getId());
            return Response.ok("Sensor assigned successfully.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to assign sensor: " + e.getMessage()).build();
        }
    }

}
