package com.ponatosik.kanban.presentation;

import com.ponatosik.kanban.core.exceptions.InvalidTaskOrderException;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.exceptions.UnknownStatusException;
import com.ponatosik.kanban.core.exceptions.UnknownTaskException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UnknownTaskException.class, UnknownGroupException.class, UnknownStatusException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        String responseMessage = ex.getMessage();
        return handleExceptionInternal(ex, responseMessage,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {InvalidTaskOrderException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String responseMessage = ex.getMessage();
        return handleExceptionInternal(ex, responseMessage,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
