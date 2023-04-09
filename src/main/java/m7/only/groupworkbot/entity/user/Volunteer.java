package m7.only.groupworkbot.entity.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * Класс сущности волонтера
 */
@Data
@AllArgsConstructor
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
}
