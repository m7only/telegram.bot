package m7.only.groupworkbot.entity.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * Класс сущности волонтера
 */
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "volunteers")
public class Volunteer extends Person {

    /**
     * Список пользователей, которых ведет волонтер
     */
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users;

    public Volunteer(Long id, String fullName, String phone, Long chatId, Set<User> users) {
        super(id, fullName, phone, chatId);
        this.users = users;
    }
}
