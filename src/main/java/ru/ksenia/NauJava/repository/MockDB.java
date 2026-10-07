package ru.ksenia.NauJava.repository;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.ksenia.NauJava.entity.Guitar;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MockDB {
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Guitar> guitarContainer(){
        return new ArrayList<>();
    }
}
