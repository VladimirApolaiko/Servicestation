package org.servicestation.config.provider.exhandlers;


import org.servicestation.config.provider.exhandlers.factory.ExceptionHandlerResponseFactory;
import org.servicestation.config.provider.exhandlers.factory.ExceptionHandlerResponseFactoryImpl;
import org.servicestation.resources.exceptions.EntityNotFoundException;

import javax.ws.rs.core.Response;

public class EntityNotFoundExceptionHandler implements javax.ws.rs.ext.ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException exception) {
        ExceptionHandlerResponseFactory exceptionHandlerResponseFactory = new ExceptionHandlerResponseFactoryImpl();
        return exceptionHandlerResponseFactory.buildResponse("Entity Not Found", exception.getMessage());
    }
}
