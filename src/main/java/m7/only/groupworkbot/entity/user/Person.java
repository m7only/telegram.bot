package m7.only.groupworkbot.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Класс, консолидирующий информацию о человеке (пользователе, волонтере). Общий для всех пользователей
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    /**
     * ФИО человека
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * Контактный номер телефона
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Идентификатор чата человека
     */
    @Column(name = "chat_id")
    private Long chatId;

    public Person(Long id) {
        this.id = id;
    }
}
