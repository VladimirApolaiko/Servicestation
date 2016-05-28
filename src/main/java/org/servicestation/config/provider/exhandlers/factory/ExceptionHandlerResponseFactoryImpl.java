package org.servicestation.config.provider.exhandlers.factory;

import org.servicestation.resources.dto.ErrorMessageDto;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ExceptionHandlerResponseFactoryImpl implements ExceptionHandlerResponseFactory {

    @Override
    public Response buildResponse(String errorType, String errorDescription) {
        ErrorMessageDto errorMessage = new ErrorMessageDto();
        errorMessage.error = errorType;
        errorMessage.error_description = errorDescription;

        return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
    }
}
