package m7.only.groupworkbot.entity.shelter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление типов животных
 */
@Getter
@AllArgsConstructor
public enum AnimalType {
    CAT("Кошки"),
    DOG("Собаки");

    private final String animalTitle;
}
