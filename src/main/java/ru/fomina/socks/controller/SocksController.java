package ru.fomina.socks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fomina.socks.dto.SocksDTO;
import ru.fomina.socks.model.Colour;
import ru.fomina.socks.model.Size;
import ru.fomina.socks.model.Socks;
import ru.fomina.socks.model.WrongQuantityException;
import ru.fomina.socks.service.SocksService;

@RestController
@RequestMapping("/socks")
@Tag(name = "Носки", description = "CRUD-операции для работы с товаром на складе")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

//    @ExceptionHandler(WrongQuantityException.class)
//    public ResponseEntity<String> handleWrongQuantityException(WrongQuantityException wrongQuantityException) {
//        return ResponseEntity.badRequest().body(wrongQuantityException.getMessage());
//    }

    @PostMapping("/add")
    @Operation(
            summary = "Регистрация прихода товара на склад",
            description = "Метод позволяет зарегистрировать приход товара с указанием цвета, размера носков, " +
                    "содержания хлопка в их составе и количества пар носков"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Приход товара успешно добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            )
    })
    public ResponseEntity<SocksDTO> addSocks(@RequestBody SocksDTO socksDTO) {
        if (socksDTO.getColour() == null || socksDTO.getSize() == null ||
                socksDTO.getCottonPart() < 0 || socksDTO.getCottonPart() > 100 || socksDTO.getQuantity() < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(socksService.addSocks(socksDTO));
    }

    @PutMapping("/take")
    @Operation(
            summary = "Регистрация отпуска товара со склада",
            description = "Метод позволяет зарегистрировать отпуск товара с указанием цвета, размера носков, " +
                    "содержания хлопка в их составе и количества пар носков"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Товар успешно отпущен со склада",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Товара нет на складе в нужном количестве, или параметры запроса имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            )
    })
    public ResponseEntity<Integer> takeSocks(@RequestBody SocksDTO socksDTO) {
        if (socksDTO.getColour() == null || socksDTO.getSize() == null ||
                socksDTO.getCottonPart() < 0 || socksDTO.getCottonPart() > 100 || socksDTO.getQuantity() < 0) {
            return ResponseEntity.badRequest().build();
        }
        Integer socksQuantity = socksService.takeSocks(socksDTO);
        return ResponseEntity.ok(socksQuantity);
    }

    @GetMapping("/get")
    @Operation(
            summary = "Получение информации о количестве товара на складе",
            description = "Метод позволяет узнать количество пар носков на складе, соответствующих заданным параметрам"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            )
    })
    public ResponseEntity<Integer> getSocksQuantity(@RequestParam(required = false, name = "colour") Colour colour,
                                          @RequestParam(required = false, name = "size") Size size,
                                          @RequestParam(required = false, name = "cottonMin") Integer cottonMin,
                                          @RequestParam(required = false, name = "cottonMax") Integer cottonMax) {
        Integer socksQuantity = socksService.getSocksQuantity(colour, size, cottonMin, cottonMax);
        if (cottonMin == null) {
            return ResponseEntity.ok(socksQuantity);
        } else if (cottonMin < 0 || cottonMin > 100) {
            return ResponseEntity.badRequest().build();
        }
        if (cottonMax == null) {
            return ResponseEntity.ok(socksQuantity);
        } else if (cottonMax < 0 || cottonMax > 100) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(socksQuantity);
    }

    @DeleteMapping("/remove")
    @Operation(
            summary = "Списание бракованного товара со склада",
            description = "Метод позволяет списать испорченные (бракованные) носки со склада"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Товар списан со склада",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            )
    })
    public ResponseEntity<String> removeDamagedSocks(@RequestBody SocksDTO socksDTO) {
        if (socksDTO.getColour() == null || socksDTO.getSize() == null ||
                socksDTO.getCottonPart() < 0 || socksDTO.getCottonPart() > 100 || socksDTO.getQuantity() < 0) {
            return ResponseEntity.badRequest().build();
        }
        socksService.removeDamagedSocks(socksDTO);
        return ResponseEntity.ok("Носки списаны со склада.");
    }
}
