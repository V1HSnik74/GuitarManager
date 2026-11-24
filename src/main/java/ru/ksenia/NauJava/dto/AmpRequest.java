package ru.ksenia.NauJava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import ru.ksenia.NauJava.enums.AmpTypes;

public record AmpRequest(
        @NotBlank(message = "Бренд усилителя обязателен")
        @Size(max = 50, message = "Название бренда не должно превышать 50 символов")
        String brand,

        @NotBlank(message = "Модель усилителя обязательна")
        @Size(max = 50, message = "Модель усилителя не должна превышать 50 символов")
        String model,

        @NotNull(message = "Тип усилителя обязателен")
        AmpTypes ampType,

        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        String description,

        @URL(message = "Некорректный формат ссылки")
        String imageUrl
) {
}
