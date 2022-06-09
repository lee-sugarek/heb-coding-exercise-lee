package com.example.hebcodingexerciselee.repositories;

import com.example.hebcodingexerciselee.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/* JPA repository that contains some of the more simple queries */
@Repository
public interface ImagesRepository extends JpaRepository<ImageEntity, Integer> {
    @Query(value = "SELECT MAX(id) FROM public.\"images\" ", nativeQuery = true)
    Integer findMaxId();
}