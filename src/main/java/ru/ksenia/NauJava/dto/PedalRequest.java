package ru.ksenia.NauJava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import ru.ksenia.NauJava.enums.PedalTypes;

public record PedalRequest(
        @NotBlank(message = "Бренд педали обязателен")
        @Size(max = 50, message = "Название бренда педали не должно превышать 50 символов")
        String brand,

        @NotBlank(message = "Модель педали обязательна")
        @Size(max = 50, message = "Модель педали не должна превышать 50 символов")
        String model,

        @NotNull(message = "Тип педали обязателен")
        PedalTypes pedalType,

        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        String description,

        @URL(message = "Некорректный формат ссылки")
        String imageUrl
) {
}
