package ru.fomina.socks.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import ru.fomina.socks.model.Colour;
import ru.fomina.socks.model.Size;
import ru.fomina.socks.model.Socks;
import ru.fomina.socks.service.SocksService;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "Носки", description = "CRUD-операции для работы с товаром на складе")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

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
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Socks> addSocks(@RequestBody Socks socks) {
        if (ObjectUtils.isEmpty(socks.getColour()) || ObjectUtils.isEmpty(socks.getSize()) ||
                socks.getCottonPart() < 0 || socks.getCottonPart() > 100 || socks.getQuantity() < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(socksService.addSocks(socks));
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
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Товара нет на складе в нужном количестве, или параметры запроса имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Socks> takeSocks(@RequestBody Socks socks) {
        if (ObjectUtils.isEmpty(socks.getColour()) || ObjectUtils.isEmpty(socks.getSize()) ||
                socks.getCottonPart() < 0 || socks.getCottonPart() > 100 || socks.getQuantity() < 0) {
            return ResponseEntity.badRequest().build();
        }
        Socks newSocks = socksService.takeSocks(socks);
        return ResponseEntity.ok(newSocks);
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
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<String> getSocks(@RequestParam(required = true) Colour colour,
                                          @RequestParam(required = true) Size size,
                                          @RequestParam(required = true) int cottonMin,
                                          @RequestParam(required = true) int cottonMax) {
        return null;
    }

    @DeleteMapping("/delete")
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
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<String> deleteSocks(@RequestBody Socks socks) {
        if (socksService.deleteSocks(socks)) {
            return ResponseEntity.ok("Носки списаны со склада");
        }
        return ResponseEntity.noContent().build();
    }
}
