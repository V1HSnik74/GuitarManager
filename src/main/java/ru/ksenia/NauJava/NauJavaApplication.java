package ru.ksenia.NauJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ksenia.NauJava.console.GuitarConsole;

@SpringBootApplication
public class NauJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NauJavaApplication.class, args)
                .getBean(GuitarConsole.class).start();
    }

}
