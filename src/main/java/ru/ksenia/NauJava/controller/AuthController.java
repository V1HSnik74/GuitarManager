package ru.ksenia.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.security.CustomUserDetails;
import ru.ksenia.NauJava.security.JwtService;
import ru.ksenia.NauJava.service.interfaces.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager){
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        userService.addNewUser(user);
        String token = jwtService.generateToken(user.getEmail());
        return Map.of("token", token);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String loginInput = request.get("login");
        String password = request.get("password");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInput, password)
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());
        return Map.of("token", token);
    }

}
