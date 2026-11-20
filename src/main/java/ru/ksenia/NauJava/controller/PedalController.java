package ru.ksenia.NauJava.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ksenia.NauJava.dto.PedalRequest;
import ru.ksenia.NauJava.dto.PedalResponse;
import ru.ksenia.NauJava.security.CustomUserDetails;
import ru.ksenia.NauJava.service.interfaces.PedalService;

import java.util.List;

@RestController
@RequestMapping("/api/pedals")
public class PedalController {
    private final PedalService pedalService;

    public PedalController(PedalService pedalService) {
        this.pedalService = pedalService;
    }

    @PostMapping
    public PedalResponse addPedal(@RequestBody PedalRequest pedal, @AuthenticationPrincipal CustomUserDetails currUser) {
        return pedalService.addPedal(pedal, currUser.getId());
    }

    @PutMapping("/{id}")
    public PedalResponse updatePedal(@RequestBody PedalRequest newData, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        return pedalService.updatePedal(newData, id, currUser.getId());
    }

    @DeleteMapping("/{id}")
    public void deletePedal(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        pedalService.deletePedal(id, currUser.getId());
    }

    @GetMapping("/{id}")
    public PedalResponse getPedalById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        return pedalService.getPedalById(id, currUser.getId());
    }

    @GetMapping
    public List<PedalResponse> getPedalsByUser(@AuthenticationPrincipal CustomUserDetails currUser) {
        return pedalService.getPedalsByUserId(currUser.getId());
    }

    @GetMapping("/admin")
    public List<PedalResponse> getAllPedalsAdmin() {
        return pedalService.getAllPedalsAdmin();
    }

    @DeleteMapping("/admin/{id}")
    public void deletePedalAdmin(@PathVariable Long id) {
        pedalService.deletePedalAdmin(id);
    }
}
