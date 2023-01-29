package ru.fomina.socks.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.fomina.socks.model.Operation;
import ru.fomina.socks.model.Socks;
import ru.fomina.socks.model.Type;
import ru.fomina.socks.service.AuditService;
import ru.fomina.socks.service.FilesService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {

    private final FilesService filesService;
    private List<Operation> operations = new ArrayList<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    public AuditServiceImpl(FilesService filesService, ObjectMapper objectMapper) {
        this.filesService = filesService;
        this.objectMapper = objectMapper;
    }


    @Override
    public void addOperationRecord(Socks socks, int quantity) {
        operationRecord(Type.ADD, socks, quantity);
        saveOperationsToFile();
    }

    @Override
    public void takeOperationRecord(Socks socks, int quantity) {
        operationRecord(Type.TAKE, socks, quantity);
        saveOperationsToFile();
    }

    @Override
    public void removeOperationRecord(Socks socks, int quantity) {
        operationRecord(Type.REMOVE, socks, quantity);
        saveOperationsToFile();
    }

    private void operationRecord(Type type, Socks socks, int quantity) {
        this.operations.add(new Operation(type, LocalDateTime.now(), quantity, socks));
    }

    private void saveOperationsToFile() {
        try {
            objectMapper.registerModules();
            String json = objectMapper.writeValueAsString(operations);
            filesService.saveOperationsToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readOperationsFromFile() {
        try {
            String json = filesService.readOperationsFromFile();
            objectMapper.findAndRegisterModules();
            operations = objectMapper.readValue(json, new TypeReference<List<Operation>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
