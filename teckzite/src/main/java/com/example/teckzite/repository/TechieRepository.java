package com.example.teckzite.repository;

import com.example.teckzite.entity.Techie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TechieRepository extends JpaRepository<Techie, Long> {
    List<Techie> findByCollege(String college);
}

