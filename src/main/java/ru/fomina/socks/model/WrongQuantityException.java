package ru.fomina.socks.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongQuantityException extends RuntimeException{
    public WrongQuantityException(String message) {
        super(message);
    }
}
