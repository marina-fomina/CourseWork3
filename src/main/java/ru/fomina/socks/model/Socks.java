package ru.fomina.socks.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class Socks {
    private final Colour colour;
    private final Size size;
    private final int cottonPart;

    public Socks(Colour colour, Size size, int cottonPart) {
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
            throw new WrongQuantityException("Содержание хлопка должно быть от 0 до 100%!");
        } else {
            this.cottonPart = cottonPart;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cottonPart == socks.cottonPart && colour == socks.colour && size == socks.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour, size, cottonPart);
    }
}
