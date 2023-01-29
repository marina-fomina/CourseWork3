package ru.fomina.socks.service;

import ru.fomina.socks.model.Socks;

public interface AuditService {
    void addOperationRecord(Socks socks, int quantity);

    void takeOperationRecord(Socks socks, int quantity);

    void removeOperationRecord(Socks socks, int quantity);
}
