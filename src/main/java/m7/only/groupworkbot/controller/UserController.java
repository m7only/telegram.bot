package m7.only.groupworkbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "Api пользователей", description = "CRUD пользователей телеграм бота")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат запроса получен"),
        @ApiResponse(responseCode = "400", description = "Невалидные входные данные"),
        @ApiResponse(responseCode = "404", description = "Результат запроса = NULL"),
        @ApiResponse(responseCode = "500", description = "Внутренний косяк сервера")
})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Добавление отчета")
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Validated User user) {
        return ResponseEntity.ok(userService.add(user));
    }

    @Operation(summary = "Список всех отчетов")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @Operation(summary = "Получение отчета по id")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.of(userService.getById(id));
    }

    @Operation(summary = "Редактирование отчета по id")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody @Valid User user) {
        return ResponseEntity.of(userService.update(id, user));
    }

    @Operation(summary = "Удаление отчета по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        return ResponseEntity.of(userService.delete(id));
    }
}
