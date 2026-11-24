package ru.ksenia.NauJava.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ksenia.NauJava.dto.AmpRequest;
import ru.ksenia.NauJava.dto.AmpResponse;
import ru.ksenia.NauJava.security.CustomUserDetails;
import ru.ksenia.NauJava.service.interfaces.AmpService;

import java.util.List;

@RestController
@RequestMapping("/api/amps")
public class AmpController {
    private final AmpService ampService;

    public AmpController(AmpService ampService) {
        this.ampService = ampService;
    }

    @PostMapping
    public AmpResponse addAmp(@Valid @RequestBody AmpRequest amp, @AuthenticationPrincipal CustomUserDetails currUser) {
        return ampService.addAmp(amp, currUser.getId());
    }

    @PutMapping("/{id}")
    public AmpResponse updateAmp(@Valid @RequestBody AmpRequest newData, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        return ampService.updateAmp(newData, id, currUser.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteAmp(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        ampService.deleteAmp(id, currUser.getId());
    }

    @GetMapping("/{id}")
    public AmpResponse getAmpById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        return ampService.getAmpById(id, currUser.getId());
    }

    @GetMapping
    public List<AmpResponse> getAllAmpsUserId(@AuthenticationPrincipal CustomUserDetails currUser) {
        return ampService.getAmpsByUserId(currUser.getId());
    }

    @GetMapping("/admin")
    public List<AmpResponse> getAllAmpsAdmin() {
        return ampService.getAllAmpsAdmin();
    }

    @DeleteMapping("/admin/{id}")
    public void deleteAmpAdmin(@PathVariable Long id) {
        ampService.deleteAmpAdmin(id);
    }
}
