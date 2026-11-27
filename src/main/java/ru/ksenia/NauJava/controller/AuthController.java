package ru.ksenia.NauJava.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.ksenia.NauJava.dto.AuthResponse;
import ru.ksenia.NauJava.dto.LoginRequest;
import ru.ksenia.NauJava.dto.RefreshRequest;
import ru.ksenia.NauJava.dto.RegisterRequest;
import ru.ksenia.NauJava.entity.RefreshToken;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.security.CustomUserDetails;
import ru.ksenia.NauJava.security.JwtService;
import ru.ksenia.NauJava.service.interfaces.RefreshTokenService;
import ru.ksenia.NauJava.service.interfaces.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager,
                          RefreshTokenService refreshTokenService){
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.addNewUser(request);
        return createTokens(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password())
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.getUserByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername());
        return createTokens(user);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody RefreshRequest request) {
        RefreshToken rotated = refreshTokenService.rotate(request.refreshToken());
        return createTokens(rotated.getUser());
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody RefreshRequest request) {
        refreshTokenService.revoke(request.refreshToken());
    }

    private AuthResponse createTokens(User user) {
        String accessToken = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.create(user);
        return new AuthResponse(accessToken, refreshToken.getRawToken(), jwtService.getExpiration() / 1000);
    }

}
