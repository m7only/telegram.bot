package m7.only.groupworkbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@Validated
@Tag(name = "Api отчетов", description = "CRUD отчетов телеграм бота")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат запроса получен"),
        @ApiResponse(responseCode = "400", description = "Невалидные входные данные"),
        @ApiResponse(responseCode = "404", description = "Результат запроса = NULL"),
        @ApiResponse(responseCode = "500", description = "Внутренний косяк сервера")
})
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Добавление отчета")
    @PostMapping
    public ResponseEntity<Report> addReport(@RequestBody @Validated Report report) {
        return ResponseEntity.ok(reportService.add(report));
    }

    @Operation(summary = "Список всех отчетов")
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAll());
    }

    @Operation(summary = "Получение отчета по id")
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return ResponseEntity.of(reportService.getById(id));
    }

    @Operation(summary = "Редактирование отчета по id")
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id,
                                               @RequestBody @Valid Report report) {
        return ResponseEntity.of(reportService.update(id, report));
    }

    @Operation(summary = "Удаление отчета по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Report> deleteReport(@PathVariable Long id) {
        return ResponseEntity.of(reportService.delete(id));
    }
}
