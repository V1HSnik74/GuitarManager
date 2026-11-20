package ru.ksenia.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ksenia.NauJava.dto.SetupRequest;
import ru.ksenia.NauJava.dto.SetupResponse;
import ru.ksenia.NauJava.security.CustomUserDetails;
import ru.ksenia.NauJava.service.interfaces.SetupService;

import java.util.List;

@RestController
@RequestMapping("/api/setups")
public class SetupController {
    private final SetupService setupService;

    @Autowired
    public SetupController(SetupService setupService) {
        this.setupService = setupService;
    }

    @PostMapping
    public SetupResponse addSetup(@RequestBody SetupRequest setup, @AuthenticationPrincipal CustomUserDetails currUser) {
        return setupService.addSetup(setup, currUser.getId());
    }

    @PutMapping("/{id}")
    public SetupResponse updateSetup(@RequestBody SetupRequest newData, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        return setupService.updateSetup(newData, id, currUser.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteSetup(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        setupService.deleteSetup(id, currUser.getId());
    }

    @GetMapping("/{id}")
    public SetupResponse getSetupById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        return setupService.getSetupById(id, currUser.getId());
    }

    @GetMapping
    public List<SetupResponse> getAllSetupsUser(@AuthenticationPrincipal CustomUserDetails currUser) {
        return setupService.getAllSetups(currUser.getId());
    }

    @GetMapping("/admin")
    public List<SetupResponse> getAllSetupsAdmin() {
        return setupService.getAllSetupsAdmin();
    }

    @DeleteMapping("/admin/{id}")
    public void deleteSetupAdmin(@PathVariable Long id) {
        setupService.deleteSetupAdmin(id);
    }
}
