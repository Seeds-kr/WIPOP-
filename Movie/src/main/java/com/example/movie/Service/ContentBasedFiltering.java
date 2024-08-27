package com.example.movie.Service;


import com.example.movie.Entity.Member;
import com.example.movie.Entity.MovieInfoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContentBasedFiltering {

    // 영화 간의 유사도를 계산하는 메서드
    private double calculateSimilarity(MovieInfoEntity movie1, MovieInfoEntity movie2) {
        // 여기서는 간단히 장르와 평점의 유사도를 계산하는 예제를 제공합니다.
        double genreSimilarity = movie1.getGenres().equalsIgnoreCase(movie2.getGenres()) ? 1.0 : 0.0;
        double ratingDifference = Math.abs(movie1.getRating() - movie2.getRating());

        return genreSimilarity * (1 - ratingDifference / 10.0);
    }

    // 사용자의 선호도에 따라 영화를 추천하는 메서드
    public List<MovieInfoEntity> recommendMovies(Member user, List<MovieInfoEntity> allMovies) {
        // 사용자가 작성한 리뷰를 통해 선호 영화 리스트를 생성
        List<MovieInfoEntity> userReviewedMovies = user.getMovies();

        return allMovies.stream()
                .filter(movie -> userReviewedMovies.stream()
                        .noneMatch(reviewedMovie -> reviewedMovie.getId().equals(movie.getId())))
                .sorted((movie1, movie2) -> {
                    double sim1 = userReviewedMovies.stream()
                            .mapToDouble(reviewedMovie -> calculateSimilarity(reviewedMovie, movie1))
                            .average().orElse(0.0);
                    double sim2 = userReviewedMovies.stream()
                            .mapToDouble(reviewedMovie -> calculateSimilarity(reviewedMovie, movie2))
                            .average().orElse(0.0);
                    return Double.compare(sim2, sim1);
                })
                .collect(Collectors.toList());
    }
}