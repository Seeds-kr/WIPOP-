package com.example.movie.Service;

import com.example.movie.Entity.GenresEntity;
import com.example.movie.repository.GenresRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenresRepo genresRepo;

    public GenresEntity findOrCreateNew(String name) {
        return genresRepo.findByName(name).orElseGet(
                () -> genresRepo.save(new GenresEntity(name))
        );
    }
}