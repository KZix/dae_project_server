package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.TipoProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.TipoProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.TipoProduto;

import java.util.List;

@Path("tipo_produtos") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class TipoProdutoService {
    @EJB
    private TipoProdutoBean tipoProdutoBean;

    @GET
    @Path("/")
    public List<TipoProdutoDTO> getAllTipoProdutos(){
        return TipoProdutoDTO.from(tipoProdutoBean.findAll());
    }

    @GET
    @Path("{tipoProdutoId}")
    public Response getProductById(@PathParam("tipoProdutoId") int id){
        return Response.ok(TipoProdutoDTO.from(tipoProdutoBean.find(id))).build();
    }

    @POST
    @Path("/")
    public Response create(TipoProdutoDTO tipoProdutoDTO) {
        try {
            TipoProduto tipoProduto = tipoProdutoBean.create(
                    tipoProdutoDTO.getNome()
            );
            return Response.status(Response.Status.CREATED).entity(TipoProdutoDTO.from(tipoProduto)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{tipoProdutoId}")
    public Response update(@PathParam("tipoProdutoId") int id, TipoProdutoDTO tipoProdutoDTO) {
        try {
            tipoProdutoBean.update(id, tipoProdutoDTO.getNome());
            TipoProduto updatedTipoProduto = tipoProdutoBean.find(id);
            if (updatedTipoProduto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("TipoProduto não encontrado").build();
            }
            return Response.ok(TipoProdutoDTO.from(updatedTipoProduto)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
