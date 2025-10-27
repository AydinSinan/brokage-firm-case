package com.pm.brokeragefirm.exception;



import com.pm.brokeragefirm.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(ChangeSetPersister.NotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(Instant.now(), 404, "Not Found", ex.getMessage(), req.getRequestURI()));
    }
    @ExceptionHandler({BusinessRuleException.class, AccessDeniedCustomerException.class})
    public ResponseEntity<ErrorResponse> badReq(RuntimeException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(Instant.now(), 400, "Bad Request", ex.getMessage(), req.getRequestURI()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> generic(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(Instant.now(), 500, "Internal Server Error", ex.getMessage(), req.getRequestURI()));
    }
}
