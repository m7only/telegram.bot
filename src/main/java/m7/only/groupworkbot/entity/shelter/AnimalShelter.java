package m7.only.groupworkbot.entity.shelter;

import jakarta.persistence.*;
import lombok.*;
import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.entity.user.User;

import java.util.Set;

/**
 * Класс сущности приюта
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal_shelters")
public class AnimalShelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Общая информация о приюте
     */
    @Column(name = "about")
    private String about;

    /**
     * Специализация приюта
     */
    @Column(name = "animal_type")
    @Enumerated(EnumType.STRING)
    private AnimalType animalType;
    /**
     * Контакты приюта
     */
    @Column(name = "contacts")
    private String contacts;

    /**
     * Адрес приюта
     */
    @Column(name = "address")
    private String address;

    /**
     * Часы работы приюта
     */
    @Column(name = "opening_hours")
    private String openingHours;

    /**
     * Правила проезда на территорию, контакты охраны для получения пропуска на автомобиль
     */
    @Column(name = "security_rules")
    private String securityRules;

    /**
     * Техника безопасности в приюте
     */
    @Column(name = "safety_rules")
    private String safetyRules;

    /**
     * Условия усыновления
     */
    @Column(name = "adoption_rules")
    private String adoptionRules;

    /**
     * Список эндпоинтов, относящихся к приюту
     */
    @OneToMany(mappedBy = "animalShelter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Endpoint> endpoints;

    @OneToMany(mappedBy = "animalShelter", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users;

    public AnimalShelter(String about, AnimalType animalType, String contacts, String address, String openingHours, String securityRules, String safetyRules, String adoptionRules) {
        this.about = about;
        this.animalType = animalType;
        this.contacts = contacts;
        this.address = address;
        this.openingHours = openingHours;
        this.securityRules = securityRules;
        this.safetyRules = safetyRules;
        this.adoptionRules = adoptionRules;
    }
}
