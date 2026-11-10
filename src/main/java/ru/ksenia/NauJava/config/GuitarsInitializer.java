package ru.ksenia.NauJava.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ksenia.NauJava.entity.Guitar;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.repository.GuitarRepository;
import ru.ksenia.NauJava.repository.UserRepository;

import java.util.List;

@Configuration
public class GuitarsInitializer {
    @Bean
    public CommandLineRunner initGuitars(GuitarRepository guitarRepository, UserRepository userRepository) {
        return args -> {
            if (guitarRepository.count() == 0) {
                User user = userRepository.findById(1L).orElseThrow();
                List<Guitar> testGuitars = List.of(
                        new Guitar("Ibanez", "Roadstar", 6,
                                "HSS", "Superstrat", null, user),
                        new Guitar("Gibson", "SG", 6, "HH", "SG", null, user),
                        new Guitar("PRS", "Custom 24", 6, "HH", "Doublecut", null, user)
                );
                guitarRepository.saveAll(testGuitars);
            }
        };
    }
}
