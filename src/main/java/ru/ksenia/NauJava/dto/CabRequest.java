package ru.ksenia.NauJava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record CabRequest(
        @NotBlank(message = "Бренд кабинета обязателен")
        @Size(max = 50, message = "Название бренда не должно превышать 50 символов")
        String brand,

        @NotBlank(message = "Модель кабинета обязательна")
        @Size(max = 50, message = "Модель кабинета не должна превышать 50 символов")
        String model,

        @Size(max = 50, message = "Название динамиков не должно превышать 50 символов")
        String speakers,

        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        String description,

        @URL(message = "Некорректный формат ссылки")
        String imageUrl
)
{}
