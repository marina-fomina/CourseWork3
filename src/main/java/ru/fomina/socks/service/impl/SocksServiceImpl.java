package ru.fomina.socks.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import ru.fomina.socks.dto.SocksDTO;
import ru.fomina.socks.model.*;
import ru.fomina.socks.service.AuditService;
import ru.fomina.socks.service.FilesService;
import ru.fomina.socks.service.SocksService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SocksServiceImpl implements SocksService {

    private final FilesService filesService;
    private final AuditService auditService;

    private HashMap<Socks, Integer> socksMap = new HashMap<>();

    @Value("src/main/resources")
    private String socksFilePath;

    @Value("socks.json")
    private String socksFileName;

    private final ObjectMapper objectMapper;

    public SocksServiceImpl(FilesService filesService, AuditService auditService, ObjectMapper objectMapper) {
        this.filesService = filesService;
        this.auditService = auditService;
        this.objectMapper = objectMapper;
    }


    @Override
    public SocksDTO addSocks(SocksDTO socksDTO) {
        Socks socks = mapToSocks(socksDTO);
        if (socksMap.containsKey(socks)) {
            socksMap.put(socks, socksMap.get(socks) + socksDTO.getQuantity());
        } else {
            socksMap.put(socks, socksDTO.getQuantity());
        }
        auditService.addOperationRecord(socks, socksDTO.getQuantity());
        saveSocksToFile();
        return socksDTO;
    }

    @Override
    public Integer takeSocks(SocksDTO socksDTO) {
        decreaseSocks(socksDTO, true);
        Socks socks = mapToSocks(socksDTO);
        saveSocksToFile();
        return socksMap.get(socks);
    }

    @Override
    public void removeDamagedSocks(SocksDTO socksDTO) {
        decreaseSocks(socksDTO, false);
    }

    private void decreaseSocks(SocksDTO socksDTO, boolean areSocksTaken) {
        Socks socks = mapToSocks(socksDTO);
        int socksQuantity = socksMap.getOrDefault(socks, 0);
        if (!socksMap.containsKey(socks)) {
            throw new WrongQuantityException("На складе закончился такой товар!");
        }
        if (socksQuantity >= socksDTO.getQuantity()) {
            socksMap.put(socks, socksMap.get(socks) - socksDTO.getQuantity());
        } else {
            throw new WrongQuantityException("На складе нет таких носков в нужном количестве!");
        }
        if (areSocksTaken) {
            auditService.takeOperationRecord(socks, socksDTO.getQuantity());
        } else {
            auditService.removeOperationRecord(socks, socksDTO.getQuantity());
        }
    }

    @Override
    public Integer getSocksQuantity(Colour colour, Size size, Integer cottonMin, Integer cottonMax) {
        int socksNumber = 0;
        for (Map.Entry<Socks, Integer> entry : socksMap.entrySet()) {
            if (colour != null && !entry.getKey().getColour().equals(colour)) {
                continue;
            }
            if (size != null && !entry.getKey().getSize().equals(size)) {
                continue;
            }
            if (cottonMin != null && entry.getKey().getCottonPart() < cottonMin) {
                continue;
            }
            if (cottonMax != null && entry.getKey().getCottonPart() > cottonMax) {
                continue;
            }
            socksNumber = socksNumber + entry.getValue();
        }
        return socksNumber;
    }

    private Socks mapToSocks(SocksDTO socksDTO) {
        return new Socks(socksDTO.getColour(), socksDTO.getSize(), socksDTO.getCottonPart());
    }

    private SocksDTO mapToDTO(Socks socks, int quantity) {
        SocksDTO dto = new SocksDTO();
        dto.setColour(socks.getColour());
        dto.setSize(socks.getSize());
        dto.setCottonPart(socks.getCottonPart());
        dto.setQuantity(quantity);
        return dto;
    }

    private FileSystemResource saveSocksToFile() {
        try {
            Path path = Path.of(socksFilePath, socksFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            List<SocksDTO> socksList = new ArrayList<>();
            for (Map.Entry<Socks, Integer> entry : socksMap.entrySet()) {
                socksList.add(mapToDTO(entry.getKey(), entry.getValue()));
            }
            Files.write(path, objectMapper.writeValueAsBytes(socksList));
            return new FileSystemResource(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void readSocksFromFile(InputStream inputStream) {
        try {
            List<SocksDTO> socksList = objectMapper.readValue(inputStream, new TypeReference<List<SocksDTO>>() {
            });
            this.socksMap.clear();
            for (SocksDTO dto : socksList) {
                addSocks(dto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
