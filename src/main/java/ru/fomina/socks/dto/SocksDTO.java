package ru.fomina.socks.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.fomina.socks.model.Colour;
import ru.fomina.socks.model.NotEnoughDataException;
import ru.fomina.socks.model.Size;
import ru.fomina.socks.model.WrongQuantityException;

@Getter
@NoArgsConstructor
public class SocksDTO {
    private Colour colour;
    private Size size;
    private int cottonPart;
    private int quantity;

    public void setColour(Colour colour) {
        if (colour == null) {
            throw new NotEnoughDataException("Цвет носков не указан!");
        } else {
            this.colour = colour;
        }
    }

    public void setSize(Size size) {
        if (size == null) {
            throw new NotEnoughDataException("Размер носков не указан!");
        } else {
            this.size = size;
        }
    }

    public void setCottonPart(int cottonPart) {
        if (cottonPart < 0 || cottonPart > 100) {
            throw new WrongQuantityException("Содержание хлопка должно быть от 0 до 100%!");
        } else {
            this.cottonPart = cottonPart;
        }
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new WrongQuantityException("Количество пар носков на складе не может быть меньше 0!");
        } else {
            this.quantity = quantity;
        }
    }
}
