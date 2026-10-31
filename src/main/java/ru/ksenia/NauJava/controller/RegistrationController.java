package ru.ksenia.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.service.UserService;

@Controller
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){
        try {
            userService.addNewUser(user);
            return "redirect:/login";
        }
        catch (Exception e){
            model.addAttribute("message", "Пользователь не добавлен");
            return "registration";
        }
    }

}
