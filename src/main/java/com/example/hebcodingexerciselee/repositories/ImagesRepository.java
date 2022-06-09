package com.example.hebcodingexerciselee.repositories;

import com.example.hebcodingexerciselee.dtos.ImageDto;
import com.example.hebcodingexerciselee.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.List;

@Repository
public interface ImagesRepository extends JpaRepository<ImageEntity, Integer> {
    @Query(value = "SELECT MAX(id) FROM public.\"images\" ", nativeQuery = true)
    Integer findMaxId();

    @Query(value = "SELECT * FROM public.\"images\" WHERE 'Apple' = ANY(objects) OR 'Fruit' = ANY(objects) ", nativeQuery = true)
    List<ImageEntity> findAllByObjects();

    @Modifying
    @Query(value = "INSERT INTO public.\"images\"(id, filename, type, source, objects) VALUES (:id, :filename, :type, :source, :objects)", nativeQuery = true)
    void insertImage(Integer id, String filename, String type, byte[] source, Array objects);
}