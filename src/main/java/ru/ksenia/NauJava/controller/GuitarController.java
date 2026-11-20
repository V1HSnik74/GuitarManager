package ru.ksenia.NauJava.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ksenia.NauJava.dto.GuitarRequest;
import ru.ksenia.NauJava.dto.GuitarResponse;
import ru.ksenia.NauJava.security.CustomUserDetails;
import ru.ksenia.NauJava.service.interfaces.GuitarService;

import java.util.List;

@RestController
@RequestMapping("/api/guitars")
public class GuitarController {
    private final GuitarService guitarService;

    public GuitarController(GuitarService guitarService) {
        this.guitarService = guitarService;
    }

    @PostMapping
    public GuitarResponse addGuitar(@RequestBody GuitarRequest guitar, @AuthenticationPrincipal CustomUserDetails currUser) {
        return guitarService.addGuitar(guitar, currUser.getId());
    }

    @PutMapping("/{id}")
    public GuitarResponse updateGuitar(@PathVariable Long id, @RequestBody GuitarRequest newData, @AuthenticationPrincipal CustomUserDetails currUser) {
        return guitarService.updateGuitar(newData, id, currUser.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteGuitar(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        guitarService.deleteGuitar(id, currUser.getId());
    }

    @GetMapping("/{id}")
    public GuitarResponse getGuitarById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails currUser) {
        return guitarService.getGuitarById(id, currUser.getId());
    }

    @GetMapping
    public List<GuitarResponse> getAllGuitarByUser(@AuthenticationPrincipal CustomUserDetails currUser) {
        return guitarService.getAllGuitarsByUserId(currUser.getId());
    }

    @GetMapping("/admin")
    public List<GuitarResponse> getAllGuitarAdmin() {
        return guitarService.getAllGuitarsAdmin();
    }

    @DeleteMapping("/admin/{id}")
    public void deleteGuitarAdmin(@PathVariable Long id) {
        guitarService.deleteGuitarAdmin(id);
    }
}
