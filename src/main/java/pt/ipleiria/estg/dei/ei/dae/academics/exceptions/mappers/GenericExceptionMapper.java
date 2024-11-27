package pt.ipleiria.estg.dei.ei.dae.academics.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.GenericException;

import java.util.logging.Logger;

public class GenericExceptionMapper implements ExceptionMapper<GenericException> {
    private static final Logger logger =
            Logger.getLogger(GenericException.class.getCanonicalName());

    @Override
    public Response toResponse(GenericException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMsg)
                .build();
    }
}
