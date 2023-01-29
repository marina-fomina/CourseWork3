package ru.fomina.socks.model;

public enum Type {
    ADD ("приемка"),
    TAKE ("выдача"),
    REMOVE ("списание");

    private final String type;

    Type(String type) {
        this.type = type;
    }
}
