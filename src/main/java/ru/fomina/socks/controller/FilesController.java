package ru.fomina.socks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.fomina.socks.dto.SocksDTO;
import ru.fomina.socks.service.FilesService;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Файлы", description = "Операции по загрузке и выгрузке файлов")
public class FilesController {

    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }


    @GetMapping(value = "/socks/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Скачивание json-файла с данными о носках")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл с данными о носках в формате JSON успешно загружен с сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Запрос успешно обработан. На сервере нет файла для ответа на запрос",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            )
    })
    public ResponseEntity<InputStreamResource> downloadSocksFile() throws FileNotFoundException {
        File file = filesService.getSocksFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"Socks.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/socks/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка на сервер нового json-файла с данными о носках")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл с данными о носках в формате JSON успешно загружен на сервер",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SocksDTO.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> uploadSocksFile(@RequestParam MultipartFile file) {
        filesService.cleanSocksFile();
        File socksFile = filesService.getSocksFile();

        try (FileOutputStream fos = new FileOutputStream(socksFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
