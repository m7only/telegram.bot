package m7.only.groupworkbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import m7.only.groupworkbot.entity.report.ReportPhoto;
import m7.only.groupworkbot.services.ReportPhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportPhoto")
@Validated
@Tag(name = "Api фотографий отчетов", description = "CRUD фотографий отчетов телеграм бота")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат запроса получен"),
        @ApiResponse(responseCode = "400", description = "Невалидные входные данные"),
        @ApiResponse(responseCode = "404", description = "Результат запроса = NULL"),
        @ApiResponse(responseCode = "500", description = "Внутренний косяк сервера")
})
public class ReportPhotoController {

    private final ReportPhotoService ReportPhotoPhotoService;

    public ReportPhotoController(ReportPhotoService reportPhotoPhotoService) {
        ReportPhotoPhotoService = reportPhotoPhotoService;
    }

    @Operation(summary = "Добавление фотографии")
    @PostMapping
    public ResponseEntity<ReportPhoto> addReportPhoto(@RequestBody @Validated ReportPhoto reportPhoto) {
        return ResponseEntity.ok(ReportPhotoPhotoService.add(reportPhoto));
    }

    @Operation(summary = "Список всех фотографии")
    @GetMapping
    public ResponseEntity<List<ReportPhoto>> getAllReportPhotos() {
        return ResponseEntity.ok(ReportPhotoPhotoService.getAll());
    }

    @Operation(summary = "Список всех фотографии по ID отчета")
    @GetMapping("/report/{reportId}")
    public ResponseEntity<List<ReportPhoto>> getAllReportPhotosByReportId(@PathVariable Long reportId) {
        return ResponseEntity.ok(ReportPhotoPhotoService.getAllByReportId(reportId));
    }

    @Operation(summary = "Получение фотографии по id")
    @GetMapping("/{id}")
    public ResponseEntity<ReportPhoto> getReportPhotoById(@PathVariable Long id) {
        return ResponseEntity.of(ReportPhotoPhotoService.getById(id));
    }

    @Operation(summary = "Редактирование фотографии по id")
    @PutMapping("/{id}")
    public ResponseEntity<ReportPhoto> updateReportPhoto(@PathVariable Long id,
                                                         @RequestBody @Valid ReportPhoto reportPhoto) {
        return ResponseEntity.of(ReportPhotoPhotoService.update(id, reportPhoto));
    }

    @Operation(summary = "Удаление фотографии по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ReportPhoto> deleteReportPhoto(@PathVariable Long id) {
        return ResponseEntity.of(ReportPhotoPhotoService.delete(id));
    }
}
