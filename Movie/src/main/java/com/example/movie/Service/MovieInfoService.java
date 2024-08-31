package com.example.movie.Service;

import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.repository.MovieInfoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieInfoService {

    private final MovieInfoRepo movieRepository;

    public MovieInfoEntity findMovie(Long movieId){
        return movieRepository.findById(movieId).orElseThrow(()
                -> new IllegalArgumentException("Invalid movie Id"));
    }
}
