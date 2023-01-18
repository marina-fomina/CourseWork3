package ru.fomina.socks.service;

import org.springframework.core.io.FileSystemResource;

import java.io.File;

public interface FilesService {

    boolean saveSocksToFile(String json);

    String readSocksFromFile();

    File getSocksFile();

    boolean cleanSocksFile();
}
