package org.servicestation.config.provider.exhandlers.factory;

import javax.ws.rs.core.Response;

public interface ExceptionHandlerResponseFactory {
    Response buildResponse(String errorType, String errorDescription);
}
