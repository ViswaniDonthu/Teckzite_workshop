package com.example.teckzite.controller;

import com.example.teckzite.entity.Techie;
//import com.example.teckzite.service.TechieAnalysisService;
import com.example.teckzite.service.TechieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/techies")
public class TechieController {
    private final TechieService service;
  //  private final TechieAnalysisService analysisService;
    public TechieController(TechieService service) {
        this.service = service;
       // this.analysisService = analysisService;
    }

    // 1️⃣ Register Techie
    @PostMapping("/register")
    public Techie registerTechie(@RequestBody Techie techie) {
        return service.registerTechie(techie);
    }

    // 2️⃣ Get All Techies
    @GetMapping
    public List<Techie> getAllTechies() {
        return service.getAllTechies();
    }

    // 3️⃣ Get Techie by ID
    @GetMapping("/{id}")
    public Techie getTechieById(@PathVariable Long id) {
        return service.getTechieById(id);
    }

    // 4️⃣ Search by College
    @GetMapping("/search")
    public List<Techie> getByCollege(@RequestParam String college) {
        return service.getTechiesByCollege(college);
    }

    // 5️⃣ Delete Techie
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

