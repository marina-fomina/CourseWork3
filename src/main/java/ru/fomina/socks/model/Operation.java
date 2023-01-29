package ru.fomina.socks.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class Operation {
    private Type type;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime dateAndTime;
    private Socks socks;
    private int quantity;

    public Operation(Type type, LocalDateTime dateAndTime, int quantity, Socks socks) {
        this.type = type;
        this.dateAndTime = dateAndTime;
        this.socks = socks;
        this.quantity = quantity;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public Socks getSocks() {
        return socks;
    }
}
