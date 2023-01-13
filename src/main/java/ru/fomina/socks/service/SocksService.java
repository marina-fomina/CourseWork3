package ru.fomina.socks.service;

import ru.fomina.socks.model.Socks;

public interface SocksService {
    Socks addSocks(Socks sock);

    Socks takeSocks(Socks socks);

    Integer getSocks(Socks socks);

    boolean deleteSocks(Socks socks);
}
