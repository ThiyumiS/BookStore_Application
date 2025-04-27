// filepath: e:\1 work\2nd yr\thiyu_CSA\BookStore_Application\src\main\java\com\BookStore\Application\Exceptions\GlobalErrorMapper.java
package com.BookStore.Application.Exceptions;

import com.BookStore.Application.Model.ErrorResponse;
import com.BookStore.Application.Util.LoggerUtil;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Global exception mapper to catch all unhandled exceptions.
 * This ensures that no raw stack traces are exposed to clients
 * and the API returns graceful JSON responses for all errors.
 */
@Provider
public class GlobalErrorMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        // Log the exception with stack trace
        LoggerUtil.logSevere("Unhandled exception occurred", exception);
        
        // Return a generic error message to the client
        ErrorResponse error = new ErrorResponse(
                "Internal Server Error",
                "An unexpected error occurred.",
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()
        );
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
