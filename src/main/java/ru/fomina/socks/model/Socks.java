package ru.fomina.socks.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Socks {
    private final Colour colour;
    private final Size size;
    private final int cottonPart;
    private final int quantity;

    public Socks(Colour colour, Size size, int cottonPart, int quantity) {
        if (colour == null) {
            throw new NotEnoughDataException("Цвет носков не указан!");
        } else {
            this.colour = colour;
        }
        if (size == null) {
            throw new NotEnoughDataException("Размер носков не указан!");
        } else {
            this.size = size;
        }
        if (cottonPart < 0 || cottonPart > 100) {
            throw new IllegalArgumentException("Содержание хлопка указано неверно!");
        } else {
            this.cottonPart = cottonPart;
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Количество пар носков на складе указано неверно!");
        }
        this.quantity = quantity;
    }
}
