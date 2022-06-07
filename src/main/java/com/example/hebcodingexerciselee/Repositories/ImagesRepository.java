package com.example.hebcodingexerciselee.Repositories;

import com.example.hebcodingexerciselee.Entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<ImageEntity, Integer> {
}