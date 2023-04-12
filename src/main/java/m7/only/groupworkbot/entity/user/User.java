package m7.only.groupworkbot.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;

import java.time.LocalDate;
import java.util.Set;

/**
 * Класс сущности пользователя (усыновителя)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bot_users")
public class User extends Person {

    /**
     * Флаг, является ли пользователь усыновителем, {@code true} - является
     */
    @Column(name = "step_parent")
    private Boolean stepParent = false;

    /**
     * Дата начала испытательного срока
     */
    @Column(name = "trial_start")
    private LocalDate trialStart;

    /**
     * Длительность испытательного срока в днях
     */
    @Column(name = "trial_period")
    private Integer trialPeriod;

    /**
     * Флаг, увеличен ли испытательный срок, {@code true} - увеличен
     */
    @Column(name = "trial_extended")
    private Boolean trialExtended = false;

    /**
     * Флаг, информирован ли пользователь об увеличении испытательного срока, {@code true} - информирован
     */
    @Column(name = "trial_extended_informed")
    private Boolean trialExtendedInformed = false;

    /**
     * Флаг, провален ли испытательный срок, {@code true} - провален
     */
    @Column(name = "trial_failure")
    private Boolean trialFailure = false;

    /**
     * Флаг, информирован ли пользователь о провале испытательного срока, {@code true} - информирован
     */
    @Column(name = "trial_failure_informed")
    private Boolean trialFailureInformed = false;

    /**
     * Флаг, пройден ли испытательный срок, {@code true} - срок пройден
     */
    @Column(name = "trial_success")
    private Boolean trialSuccess = false;

    /**
     * Флаг, информирован ли пользователь об окончании испытательного срока, {@code true} - информирован
     */
    @Column(name = "trial_success_informed")
    private Boolean trialSuccessInformed = false;

    @Column(name = "dialog")
    @Enumerated(EnumType.STRING)
    private Dialog dialog;

    /**
     * Приют, к которому относится пользователь
     */
    @ManyToOne
    @JoinColumn(name = "animal_shelter_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private AnimalShelter animalShelter;

    /**
     * Волонтер, который ведет этого пользователя
     */
    @ManyToOne
    @JoinColumn(name = "volunteer_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Volunteer volunteer;

    /**
     * Список отчетов пользователя
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Report> reports;

    public User(Long chatId) {
        super(chatId);
    }
}
