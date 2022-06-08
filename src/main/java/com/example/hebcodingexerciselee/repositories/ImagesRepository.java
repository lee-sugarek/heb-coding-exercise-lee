package com.example.hebcodingexerciselee.repositories;

import com.example.hebcodingexerciselee.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<ImageEntity, Integer> {
    @Query(value = "SELECT MAX(id) FROM public.\"Images\" ", nativeQuery = true)
    Integer findMaxId();
}