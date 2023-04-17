package m7.only.groupworkbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.services.AnimalShelterService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animalShelter")
@Validated
@Tag(name = "Api приютов", description = "CRUD отчетов телеграм бота")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат запроса получен"),
        @ApiResponse(responseCode = "400", description = "Невалидные входные данные"),
        @ApiResponse(responseCode = "404", description = "Результат запроса = NULL"),
        @ApiResponse(responseCode = "500", description = "Внутренний косяк сервера")
})
public class AnimalShelterController {

    private final AnimalShelterService animalShelterService;

    public AnimalShelterController(AnimalShelterService animalShelterService) {
        this.animalShelterService = animalShelterService;
    }

    @Operation(summary = "Добавление приюта")
    @PostMapping
    public ResponseEntity<AnimalShelter> addAnimalShelter(@RequestBody @Validated AnimalShelter animalShelter) {
        return ResponseEntity.ok(animalShelterService.add(animalShelter));
    }

    @Operation(summary = "Список всех приютов")
    @GetMapping
    public ResponseEntity<List<AnimalShelter>> getAllAnimalShelters() {
        return ResponseEntity.ok(animalShelterService.getAll());
    }

    @Operation(summary = "Получение приюта по id")
    @GetMapping("/{id}")
    public ResponseEntity<AnimalShelter> getAnimalShelterById(@PathVariable Long id) {
        return ResponseEntity.of(animalShelterService.getById(id));
    }

    @Operation(summary = "Редактирование приюта по id")
    @PutMapping("/{id}")
    public ResponseEntity<AnimalShelter> updateAnimalShelter(@PathVariable Long id,
                                                             @RequestBody @Valid AnimalShelter animalShelter) {
        return ResponseEntity.of(animalShelterService.update(id, animalShelter));
    }

    @Operation(summary = "Удаление приюта по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<AnimalShelter> deleteAnimalShelter(@PathVariable Long id) {
        return ResponseEntity.of(animalShelterService.delete(id));
    }
}
