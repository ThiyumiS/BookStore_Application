package com.BookStore.Application.Exceptions;

import com.BookStore.Application.Model.ErrorResponse;
import com.BookStore.Application.Util.LoggerUtil;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {

    @Override
    public Response toResponse(InvalidInputException exception) {
        // Log the exception
        LoggerUtil.logWarning("Invalid input: " + exception.getMessage());
        
        ErrorResponse error = new ErrorResponse(
                "Bad Request",
                exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode()
        );
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
