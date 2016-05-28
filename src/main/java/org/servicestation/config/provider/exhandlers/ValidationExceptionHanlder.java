package org.servicestation.config.provider.exhandlers;

import org.servicestation.config.provider.exhandlers.factory.ExceptionHandlerResponseFactory;
import org.servicestation.config.provider.exhandlers.factory.ExceptionHandlerResponseFactoryImpl;
import org.servicestation.resources.exceptions.ValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionHanlder implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        ExceptionHandlerResponseFactory exceptionHandlerResponseFactory = new ExceptionHandlerResponseFactoryImpl();
        return exceptionHandlerResponseFactory.buildResponse("Validation Error", exception.getMessage());
    }

}
