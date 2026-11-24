package ru.ksenia.NauJava.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record SetupRequest(
        @NotBlank(message = "Название пресета обязательно")
        @Size(max = 50, message = "Название пресета не должно превышать 50 символов")
        String name,

        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        String description,

        @Positive(message = "ID гитары должен быть положительным")
        Long guitarId,

        @Positive(message = "ID усилителя должен быть положительным")
        Long ampId,

        @Positive(message = "ID кабинета должно быть положительным")
        Long cabId,

        @Valid
        AmpConfig ampConfig,

        @Valid
        List<PedalSlotRequest> pedalboard,

        @URL(message = "Некорректный формат ссылки")
        String imageUrl
) {
}
