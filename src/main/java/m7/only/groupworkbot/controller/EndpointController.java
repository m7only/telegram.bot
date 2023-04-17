package m7.only.groupworkbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.services.EndpointService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/endpoint")
@Validated
@Tag(name = "Api эндпоинтов", description = "CRUD эндпоинтов телеграм бота")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат запроса получен"),
        @ApiResponse(responseCode = "400", description = "Невалидные входные данные"),
        @ApiResponse(responseCode = "404", description = "Результат запроса = NULL"),
        @ApiResponse(responseCode = "500", description = "Внутренний косяк сервера")
})
public class EndpointController {
    private final EndpointService endpointService;

    public EndpointController(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @Operation(summary = "Добавление эндпоинта")
    @PostMapping
    public ResponseEntity<Endpoint> addEndpoint(@RequestBody @Validated Endpoint endpoint) {
        Optional<Endpoint> optionalEndpoint = endpointService.add(endpoint);
        return optionalEndpoint.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(endpoint));
    }

    @Operation(summary = "Список всех эндпоинтов")
    @GetMapping
    public ResponseEntity<List<Endpoint>> getAllEndpoints() {
        return ResponseEntity.ok(endpointService.getAll());
    }

    @Operation(summary = "Получение эндпоинта по id")
    @GetMapping("/{id}")
    public ResponseEntity<Endpoint> getEndpointById(@PathVariable Long id) {
        return ResponseEntity.of(endpointService.getById(id));
    }

    @Operation(summary = "Редактирование эндпоинта по id")
    @PutMapping("/{id}")
    public ResponseEntity<Endpoint> updateEndpoint(@PathVariable Long id,
                                                   @RequestBody @Valid Endpoint endpoint) {
        return ResponseEntity.of(endpointService.update(id, endpoint));
    }

    @Operation(summary = "Удаление эндпоинта по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Endpoint> deleteEndpoint(@PathVariable Long id) {
        return ResponseEntity.of(endpointService.delete(id));
    }
}
