package ru.fomina.socks.service;

import org.springframework.core.io.FileSystemResource;

import java.io.File;

public interface FilesService {

    boolean saveSocksToFile(String json);

    boolean saveOperationsToFile(String json);

    String readSocksFromFile();

    File getSocksFile();

    boolean cleanOperationsFile();

    File getOperationsFile();

    String readOperationsFromFile();

    boolean cleanSocksFile();
}
