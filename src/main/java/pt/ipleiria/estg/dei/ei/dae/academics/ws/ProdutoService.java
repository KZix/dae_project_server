package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import jakarta.ejb.EJB;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.academics.security.TokenIssuer;

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
}
