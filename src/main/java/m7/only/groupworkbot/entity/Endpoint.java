package m7.only.groupworkbot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;

import java.util.HashSet;
import java.util.Set;


/**
 * Класс сущности эндпоинта
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endpoints")
public class Endpoint {
    /**
     * Идентификатор эндпоинта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    /**
     * Текстовое представление эндпоинта {@code "/start", "/mainMenu", "/pray" и т.д.}
     */
    @Column(name = "endpoint_text")
    @NotEmpty
    private String endpointText;

    /**
     * Заголовок ссылки/кнопки
     */
    @Column(name = "title")
    @NotEmpty
    private String title;

    /**
     * Текстовое сообщение в эндпоинте
     */
    @Column(name = "content")
    @NotEmpty
    private String content;

    /**
     * Приют
     */
    @ManyToOne
    @JoinColumn(name = "animal_shelter_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotEmpty
    private AnimalShelter animalShelter;

    /**
     * Ссылка на родительский эндпоинт
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    private Endpoint parent;

    /**
     * Ссылка на дочерние эндпоинты (те, на которые ссылается текущий)
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "endpoint_relations",
            joinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id")
    )
    private Set<Endpoint> child = new HashSet<>();

    public Endpoint(String endpointText, String title, String content, AnimalShelter animalShelter, Endpoint parent) {
        this.endpointText = endpointText;
        this.title = title;
        this.content = content;
        this.animalShelter = animalShelter;
        this.parent = parent;
    }
}
