package com.example.movie.repository;


import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByMovie(MovieInfoEntity movie);
}
