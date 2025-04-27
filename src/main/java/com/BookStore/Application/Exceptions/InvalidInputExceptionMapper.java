package com.BookStore.Application.Exceptions;

import com.BookStore.Application.Model.ErrorResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {

    @Override
    public Response toResponse(InvalidInputException exception) {
        ErrorResponse error = new ErrorResponse(
                "Bad Request",
                exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode()
        );
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build();
    }
}
