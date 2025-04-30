package com.fescode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoriaExistenteException extends RuntimeException {
    public CategoriaExistenteException(String message) {
        super(message);
    }
}
