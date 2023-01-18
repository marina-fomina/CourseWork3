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
//    private final ObjectMapper objectMapper;
//    private final HashMap<Socks, Integer> socksMap = new HashMap<>();

//    public FilesServiceImpl(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }

//    @Override
//    public FileSystemResource exportFile() {
//        try {
//            Path path = Path.of(socksFilePath, socksFileName);
//                    Files.createFile(path);
//            List<SocksDTO> socksList = new ArrayList<>();
//            for (Map.Entry<Socks, Integer> entry : socksMap.entrySet()) {
//                socksList.add(mapToDTO(entry.getKey(), entry.getValue()));
//            }
//            Files.write(path, objectMapper.writeValueAsBytes(socksList));
//            return new FileSystemResource(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

}
