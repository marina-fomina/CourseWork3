package ru.fomina.socks.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fomina.socks.service.FilesService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value("src/main/resources")
    private String socksFilePath;

    @Value("socks.json")
    private String socksFileName;

    @Value("src/main/resources")
    private String operationsFilePath;

    @Value("operations.json")
    private String operationsFileName;

    @Override
    public boolean saveSocksToFile(String json) {
        try {
            cleanSocksFile();
            Files.writeString(Path.of(socksFilePath, socksFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readSocksFromFile() {
        try {
            return Files.readString(Path.of(socksFilePath, socksFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getSocksFile() {
        return new File(socksFilePath + "/" + socksFileName);
    }


    @Override
    public boolean saveOperationsToFile(String json) {
        try {
            cleanOperationsFile();
            Files.writeString(Path.of(operationsFilePath, operationsFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readOperationsFromFile() {
        try {
            return Files.readString(Path.of(operationsFilePath, operationsFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanSocksFile() {
        Path path = Path.of(socksFilePath, socksFileName);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cleanOperationsFile() {
        Path path = Path.of(operationsFilePath, operationsFileName);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getOperationsFile() {
        return new File(operationsFilePath + "/" + operationsFileName);
    }

}
