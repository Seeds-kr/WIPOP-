package com.example.movie.repository;


import com.example.movie.Entity.MovieEntity;
import com.example.movie.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByMovie(MovieEntity movie);
}
