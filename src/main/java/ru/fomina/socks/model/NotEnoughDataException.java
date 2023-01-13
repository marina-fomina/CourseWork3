package ru.fomina.socks.model;

public class NotEnoughDataException extends RuntimeException {
    public NotEnoughDataException() {
    }

    public NotEnoughDataException(String message) {
        super(message);
    }
}
