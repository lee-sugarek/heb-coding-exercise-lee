package com.example.hebcodingexerciselee.Repositories;

import com.example.hebcodingexerciselee.Entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Integer> {
}