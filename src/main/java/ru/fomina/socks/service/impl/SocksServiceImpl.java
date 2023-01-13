package ru.fomina.socks.service.impl;

import org.springframework.stereotype.Service;
import ru.fomina.socks.model.Socks;
import ru.fomina.socks.service.SocksService;

import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
public class SocksServiceImpl implements SocksService {

    private Integer socksQuantity;
    private HashMap<Socks, Integer> socksMap = new HashMap<>();


    @Override
    public Socks addSocks(Socks socks) {
        socksMap.getOrDefault(socks, socks.getQuantity());
        socksMap.put(socks, socks.getQuantity());
        return socks;
    }

    @Override
    public Socks takeSocks(Socks socks) {
        if (socksMap.isEmpty()) {
            throw new NoSuchElementException("На складе закончился весь товар!");
        }
        if (socksMap.containsKey(socks)) {
            int newQuantity = socksMap.get(socks) - socks.getQuantity();
            socksMap.put(socks, newQuantity);
            return socks;
        } else {
            throw new NoSuchElementException("На складе нет нужного товара!");
        }
    }

    @Override
    public Integer getSocks(Socks socks) {
        return socksMap.getOrDefault(socks, 0);
    }

    @Override
    public boolean deleteSocks(Socks socks) {
        if (socksMap.containsKey(socks)) {
            socksMap.remove(socks);
            return true;
        } else {
            return false;
        }
    }
}
