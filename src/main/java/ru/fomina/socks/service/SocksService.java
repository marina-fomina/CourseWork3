package ru.fomina.socks.service;

import org.springframework.core.io.FileSystemResource;
import ru.fomina.socks.dto.SocksDTO;
import ru.fomina.socks.model.Colour;
import ru.fomina.socks.model.Size;
import ru.fomina.socks.model.Socks;

import java.io.File;
import java.io.IOException;

public interface SocksService {

    SocksDTO addSocks(SocksDTO socksDTO);

    Integer takeSocks(SocksDTO socksDTO);

    void removeDamagedSocks(SocksDTO socksDTO);

    Integer getSocksQuantity(Colour colour, Size size, Integer cottonMin, Integer cottonMax);

}
