package ru.fomina.socks.service.impl;

import org.springframework.stereotype.Service;
import ru.fomina.socks.dto.SocksDTO;
import ru.fomina.socks.model.Colour;
import ru.fomina.socks.model.Size;
import ru.fomina.socks.model.Socks;
import ru.fomina.socks.model.WrongQuantityException;
import ru.fomina.socks.service.SocksService;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class SocksServiceImpl implements SocksService {
    private HashMap<Socks, Integer> socksMap = new HashMap<>();


    @Override
    public SocksDTO addSocks(SocksDTO socksDTO) {
        Socks socks = mapToSocks(socksDTO);
        if (socksMap.containsKey(socks)) {
            socksMap.put(socks, socksMap.get(socks) + socksDTO.getQuantity());
        } else {
            socksMap.put(socks, socksDTO.getQuantity());
        }
        return socksDTO;
    }

    @Override
    public Integer takeSocks(SocksDTO socksDTO) {
        decreaseSocks(socksDTO);
        Socks socks = mapToSocks(socksDTO);
        return socksMap.get(socks);
    }

    @Override
    public void removeDamagedSocks(SocksDTO socksDTO) {
        decreaseSocks(socksDTO);
    }

    private void decreaseSocks(SocksDTO socksDTO) {
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

    private SocksDTO mapToDTO(Socks socks) {
        SocksDTO dto = new SocksDTO();
        dto.setColour(socks.getColour());
        dto.setSize(socks.getSize());
        dto.setCottonPart(socks.getCottonPart());
        return dto;
    }
}
