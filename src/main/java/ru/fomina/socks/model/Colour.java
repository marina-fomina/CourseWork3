package ru.fomina.socks.model;

public enum Colour {
    WHITE ("белый"),
    BLACK ("чёрный"),
    RED ("красный"),
    GREEN ("зелёный"),
    YELLOW ("жёлтый"),
    BLUE ("синий");

    private final String colour;

    Colour(String colour) {
        this.colour = colour;
    }
}
