package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import jakarta.ejb.EJB;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("produtos") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})// injects header “Accept: application/json”

public class ProdutoService {
    @EJB
    private ProdutoBean produtoBean;

    @GET
    @Path("/")
    public List<ProdutoDTO> getAllProducts(){
        return ProdutoDTO.from(produtoBean.findAll());
    }

    @GET
    @Path("{produtoId}")
    public Response getProductById(@PathParam("produtoId") int id){
        return Response.ok(ProdutoDTO.from(produtoBean.find(id))).build();
    }

    @POST
    @Path("/")
    public Response create(ProdutoDTO produtoDTO) {
        try {
            Produto produto = produtoBean.create(
                    produtoDTO.getNome(),
                    produtoDTO.getPreco(),
                    produtoDTO.getTipoProduto()
            );
            return Response.status(Response.Status.CREATED).entity(ProdutoDTO.from(produto)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{produtoId}")
    public Response update(@PathParam("produtoId") int id, ProdutoDTO produtoDTO) {
        try {
            produtoBean.update(id, produtoDTO.getNome(), produtoDTO.getPreco(), produtoDTO.getTipoProduto());
            Produto updatedProduto = produtoBean.find(id);
            if (updatedProduto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Produto não encontrado").build();
            }
            return Response.ok(ProdutoDTO.from(updatedProduto)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{produtoId}")
    public Response delete(@PathParam("produtoId") int id) {
        Produto produto = produtoBean.find(id);
        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Produto não encontrado").build();
        }
        produtoBean.delete(id);
        return Response.noContent().build();
    }

}
