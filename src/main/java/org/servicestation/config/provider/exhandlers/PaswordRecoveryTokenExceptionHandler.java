package org.servicestation.config.provider.exhandlers;

import org.servicestation.config.provider.exhandlers.factory.ExceptionHandlerResponseFactory;
import org.servicestation.config.provider.exhandlers.factory.ExceptionHandlerResponseFactoryImpl;
import org.servicestation.resources.exceptions.PasswordRecoveryTokenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class PaswordRecoveryTokenExceptionHandler implements ExceptionMapper<PasswordRecoveryTokenException> {

    @Override
    public Response toResponse(PasswordRecoveryTokenException exception) {
        ExceptionHandlerResponseFactory exceptionHandlerResponseFactory = new ExceptionHandlerResponseFactoryImpl();
        return exceptionHandlerResponseFactory.buildResponse("Password Recovery Token Error", exception.getMessage());
    }
}
