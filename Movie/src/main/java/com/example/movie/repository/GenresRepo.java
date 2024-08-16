package com.example.movie.repository;

import com.example.movie.Entity.GenresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenresRepo extends JpaRepository<GenresEntity, Long> {

    Optional<GenresEntity> findByName(String name);

}
