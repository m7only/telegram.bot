package m7.only.groupworkbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import m7.only.groupworkbot.entity.user.Volunteer;
import m7.only.groupworkbot.services.VolunteerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteer")
@Validated
@Tag(name = "Api волонтеров", description = "CRUD волонтеров телеграм бота")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат запроса получен"),
        @ApiResponse(responseCode = "400", description = "Невалидные входные данные"),
        @ApiResponse(responseCode = "404", description = "Результат запроса = NULL"),
        @ApiResponse(responseCode = "500", description = "Внутренний косяк сервера")
})
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(summary = "Добавление отчета")
    @PostMapping
    public ResponseEntity<Volunteer> addVolunteer(@RequestBody @Validated Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.add(volunteer));
    }

    @Operation(summary = "Список всех отчетов")
    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        return ResponseEntity.ok(volunteerService.getAll());
    }

    @Operation(summary = "Получение отчета по id")
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id) {
        return ResponseEntity.of(volunteerService.getById(id));
    }

    @Operation(summary = "Редактирование отчета по id")
    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(@PathVariable Long id,
                                                     @RequestBody @Valid Volunteer volunteer) {
        return ResponseEntity.of(volunteerService.update(id, volunteer));
    }

    @Operation(summary = "Удаление отчета по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Volunteer> deleteVolunteer(@PathVariable Long id) {
        return ResponseEntity.of(volunteerService.delete(id));
    }
}
