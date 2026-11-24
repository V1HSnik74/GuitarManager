package ru.ksenia.NauJava.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ksenia.NauJava.dto.CabRequest;
import ru.ksenia.NauJava.dto.CabResponse;
import ru.ksenia.NauJava.entity.Cab;
import ru.ksenia.NauJava.security.CustomUserDetails;
import ru.ksenia.NauJava.service.interfaces.CabService;

import java.util.List;

@RestController
@RequestMapping("/api/cabs")
public class CabController {
    private final CabService cabService;

    public CabController(CabService cabService) {
        this.cabService = cabService;
    }

    @PostMapping
    public CabResponse addCab(@Valid @RequestBody CabRequest cab, @AuthenticationPrincipal CustomUserDetails currUser) {
        return cabService.addCab(cab, currUser.getId());
    }

    @PutMapping("/{id}")
    public CabResponse updateCab(@Valid @RequestBody CabRequest newData, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        return cabService.updateCab(newData, id, currUser.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteCab(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        cabService.deleteCab(id, currUser.getId());
    }

    @GetMapping("/{id}")
    public CabResponse getCabById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        return cabService.getCabById(id, currUser.getId());
    }

    @GetMapping
    public List<CabResponse> getAllCabsUserId(@AuthenticationPrincipal CustomUserDetails currUser) {
        return cabService.getAllCabsByUser(currUser.getId());
    }

    @GetMapping("/admin")
    public List<CabResponse> getAllCabsAdmin() {
        return cabService.getAllCabsAdmin();
    }

    @DeleteMapping("/admin/{id}")
    public void deleteCabAdmin(@PathVariable Long id) {
        cabService.deleteCabAdmin(id);
    }
}
