package ru.ksenia.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ksenia.NauJava.entity.Setup;
import ru.ksenia.NauJava.enums.PedalTypes;
import ru.ksenia.NauJava.repository.SetupRepository;

import java.util.List;

@RestController
@RequestMapping("/custom/setups")
public class SetupController {
    private final SetupRepository setupRepository;

    @Autowired
    public SetupController(SetupRepository setupRepository) {
        this.setupRepository = setupRepository;
    }

    @GetMapping("findByPedalType")
    public List<Setup> findByPedalType(@RequestParam PedalTypes type) {
        return setupRepository.findAllContainingPedalType(type);
    }

    @GetMapping("existsByUserAndName")
    public boolean existsByUserAndName(@RequestParam Long userId, @RequestParam String name) {
        return setupRepository.existsByUserIdAndName(userId, name);
    }
}
