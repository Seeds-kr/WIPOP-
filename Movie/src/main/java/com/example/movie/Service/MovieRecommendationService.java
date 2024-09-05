package com.example.movie.Service;

import com.example.movie.Entity.Member;
import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.dto.MovieInfoDTO;
import com.example.movie.repository.MovieInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieRecommendationService {

    private final ContentBasedFiltering contentBasedFiltering;
    private final MovieInfoRepo movieInfoRepository;
    private final MovieListService movieListService;

    @Autowired
    public MovieRecommendationService(ContentBasedFiltering contentBasedFiltering, MovieInfoRepo movieInfoRepository, MovieListService movieListService) {
        this.contentBasedFiltering = contentBasedFiltering;
        this.movieInfoRepository = movieInfoRepository;
        this.movieListService = movieListService;
    }

    public List<MovieInfoEntity> getRecommendedMovies(Member user) {
        // 전체 영화 목록을 가져옵니다.
        List<MovieInfoEntity> allMovies = movieInfoRepository.findAll();

        // ContentBasedFiltering을 사용하여 사용자에게 맞는 영화를 추천합니다.
        return contentBasedFiltering.recommendMovies(user, allMovies);
    }
}
