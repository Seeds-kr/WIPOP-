package com.example.movie.repository;

import com.example.movie.Entity.ViewListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViewListRepository extends JpaRepository<ViewListEntity, Long> {
    Optional<ViewListEntity> findByMemberId(Long memberId);
}