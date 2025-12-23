package com.example.teckzite.controller;

import com.example.teckzite.entity.Techie;
import com.example.teckzite.service.TechieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/techies")
public class TechieController {
    private final TechieService service;
    public TechieController(TechieService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public Techie registerTechie(@RequestBody Techie techie) {
        return service.registerTechie(techie);
    }

    @GetMapping
    public List<Techie> getAllTechies() {
        return service.getAllTechies();
    }

    @GetMapping("/{id}")
    public Techie getTechieById(@PathVariable Long id) {
        return service.getTechieById(id);
    }

    @GetMapping("/search")
    public List<Techie> getByCollege(@RequestParam String college) {
        return service.getTechiesByCollege(college);
    }

    @DeleteMapping("/{id}")
    public String deleteTechie(@PathVariable Long id) {
        service.deleteTechie(id);
        return "Techie deleted successfully";
    }
    @GetMapping("/analyze/{id}")
    public ResponseEntity<String> analyzeTechie(@PathVariable Long id) {
        String result = service.analyzeTechie(id);
        return ResponseEntity.ok(result);
    }
}

