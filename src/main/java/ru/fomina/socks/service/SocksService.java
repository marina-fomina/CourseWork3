package ru.fomina.socks.service;

import ru.fomina.socks.dto.SocksDTO;
import ru.fomina.socks.model.Colour;
import ru.fomina.socks.model.Size;
import ru.fomina.socks.model.Socks;

public interface SocksService {

    SocksDTO addSocks(SocksDTO socksDTO);

    Integer takeSocks(SocksDTO socksDTO);

    void removeDamagedSocks(SocksDTO socksDTO);

    Integer getSocksQuantity(Colour colour, Size size, Integer cottonMin, Integer cottonMax);
}
