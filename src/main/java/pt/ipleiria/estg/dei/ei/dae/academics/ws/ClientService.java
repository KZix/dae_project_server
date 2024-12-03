package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.annotation.security.RolesAllowed;
//import jakarta.mail.MessagingException;
import jakarta.mail.MessagingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
//import pt.ipleiria.estg.dei.ei.dae.academics.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ClientDTO;
//import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SubjectDTO;
//import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ClientBean;

import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("client") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed({"Teacher", "Administrator"})
public class ClientService {
    @EJB
    private ClientBean clientBean;
    @EJB
    private EmailBean emailBean;

    @Context
    private SecurityContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/clients/”
    public List<ClientDTO> getAllClients() {
        return ClientDTO.from(clientBean.findAll());
    }
    @POST
    @Path("/")
    public Response create(ClientDTO clientDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        clientBean.create(
                clientDTO.getUsername(),
                clientDTO.getPassword(),
                clientDTO.getName(),
                clientDTO.getEmail()
        );

        Client client = clientBean.find(clientDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(ClientDTO.from
                (client)).build();
    }
    @GET
    @Path("{username}")
    public Response getClient(@PathParam("username") String username) {
        var principal = securityContext.getUserPrincipal();

        if(!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        var client = clientBean.find(username);
        var clientDTO = ClientDTO.from(client);
        return Response.ok(clientDTO).build();
    }

    @PUT
    @Path("{username}")
    public Response update(@PathParam("username")String username, ClientDTO clientDTO) {
        clientBean.update(
                username,
                clientDTO.getPassword(),
                clientDTO.getName(),
                clientDTO.getEmail(),
                clientDTO.getCourseCode()
        );
        // Retrieve the updated client for response
        var client = clientBean.find(username);

        // Convert the updated client to DTO and return the response
        return Response.ok(ClientDTO.from(client)).build();
    }

    @POST
    @Path("/{username}/email")
    public Response sendEmail(@PathParam("username") String username, EmailDTO email)
            throws MyEntityNotFoundException, MessagingException {

        Client client = clientBean.find(username);
        emailBean.send(client.getEmail(), email.getSubject(), email.getBody());
        return Response.status(Response.Status.OK).entity("E-mail sent").build();
    }

}
