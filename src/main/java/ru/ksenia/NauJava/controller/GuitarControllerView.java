package ru.ksenia.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ksenia.NauJava.entity.Guitar;
import ru.ksenia.NauJava.repository.GuitarRepository;

@Controller
@RequestMapping("/custom/guitars/view")
public class GuitarControllerView {
    private final GuitarRepository guitarRepository;

    @Autowired
    public GuitarControllerView(GuitarRepository guitarRepository) {
        this.guitarRepository = guitarRepository;
    }

    @GetMapping("/list")
    public String guitarListView(Model model){
        Iterable<Guitar> guitars = guitarRepository.findAll();
        model.addAttribute("guitars", guitars);
        return "guitarList";
    }
}
