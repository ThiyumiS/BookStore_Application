package com.BookStore.Application.Exceptions;

import com.BookStore.Application.Model.ErrorResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {

    @Override
    public Response toResponse(OutOfStockException exception) {
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
