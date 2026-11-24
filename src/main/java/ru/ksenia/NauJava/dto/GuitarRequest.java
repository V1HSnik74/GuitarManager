package ru.ksenia.NauJava.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public record GuitarRequest(
        @NotBlank(message = "Бренд гитары обязателен")
        @Size(max = 50, message = "Название бренда не должно превышать 50 символов")
        String brand,

        @NotBlank(message = "Модель гитары обязательна")
        @Size(max = 50, message = "Модель гитары не должна превышать 50 символов")
        String model,

        @NotNull(message = "Количество струн обязательно")
        @Positive(message = "Количество струн должно быть больше 0")
        Integer stringsCount,

        @NotBlank(message = "Тип звукоснимателей обязателен")
        @Size(max = 30, message = "Тип звукоснимателей не должен превышать 30 символов")
        String pickupType,

        @NotBlank(message = "Тип гитары обязателен")
        @Size(max = 30, message = "Тип гитары не должен превышать 30 символов")
        String guitarType,

        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        String description,

        @URL(message = "Некорректный формат ссылки")
        String imageUrl
) {}
