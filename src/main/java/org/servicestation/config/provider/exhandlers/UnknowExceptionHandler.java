package org.servicestation.config.provider.exhandlers;

import org.servicestation.config.provider.exhandlers.factory.ExceptionHandlerResponseFactory;
import org.servicestation.config.provider.exhandlers.factory.ExceptionHandlerResponseFactoryImpl;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UnknowExceptionHandler implements ExceptionMapper<Exception>{

    @Override
    public Response toResponse(Exception exception) {
        ExceptionHandlerResponseFactory exceptionHandlerResponseFactory = new ExceptionHandlerResponseFactoryImpl();
        return exceptionHandlerResponseFactory.buildResponse("Unknown Error", exception.getMessage());
    }
}
