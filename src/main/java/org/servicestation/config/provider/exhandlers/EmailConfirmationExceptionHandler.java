package org.servicestation.config.provider.exhandlers;

import org.servicestation.config.provider.exhandlers.factory.ExceptionHandlerResponseFactory;
import org.servicestation.config.provider.exhandlers.factory.ExceptionHandlerResponseFactoryImpl;
import org.servicestation.resources.exceptions.EmailConfirmationTokenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class EmailConfirmationExceptionHandler implements ExceptionMapper<EmailConfirmationTokenException> {

    @Override
    public Response toResponse(EmailConfirmationTokenException exception) {
        ExceptionHandlerResponseFactory exceptionHandlerResponseFactory = new ExceptionHandlerResponseFactoryImpl();

        return exceptionHandlerResponseFactory.buildResponse("Email Confirmation Error", exception.getMessage());
    }
}
