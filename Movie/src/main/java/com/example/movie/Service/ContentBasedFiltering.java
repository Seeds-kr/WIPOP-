package com.example.movie.Service;


import com.example.movie.Entity.Member;
import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.Entity.Review;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContentBasedFiltering {

    // 영화 간의 유사도를 계산하는 메서드
    public double calculateSimilarity(MovieInfoEntity movie1, MovieInfoEntity movie2) {
        String genres1 = movie1.getGenres();
        String genres2 = movie2.getGenres();

        // 장르가 null인지 확인 후 처리
        if (genres1 == null || genres2 == null) {
            return 0.0; // 하나라도 null이면 유사도 0으로 반환
        }

        // 장르 비교 로직 (예시로 equalsIgnoreCase 사용)
        if (genres1.equalsIgnoreCase(genres2)) {
            return 1.0; // 장르가 같으면 유사도 1.0
        }

        return 0.0; // 장르가 다르면 유사도 0.0
    }

    // 사용자의 선호도에 따라 영화를 추천하는 메서드
    public List<MovieInfoEntity> recommendMovies(Member user, List<MovieInfoEntity> allMovies) {
        List<MovieInfoEntity> userReviewedMovies = user.getReviews().stream()
                .map(Review::getMovie)  // 각 리뷰에서 영화 정보를 추출
                .collect(Collectors.toList());

        return allMovies.stream()
                .filter(movie -> userReviewedMovies.stream()
                        .noneMatch(reviewedMovie -> reviewedMovie.getId().equals(movie.getId())))
                .filter(movie -> movie.getRating() > 0)  // 레이팅이 0보다 큰 영화만 포함
                .sorted((movie1, movie2) -> {
                    double sim1 = userReviewedMovies.stream()
                            .mapToDouble(reviewedMovie -> {
                                // 사용자의 리뷰에서 평점을 가져오고 유사도에 반영
                                Review review = findReviewByMovie(user, reviewedMovie);
                                double scoreWeight = (review != null) ? review.getScore() : 1.0;
                                return calculateSimilarity(reviewedMovie, movie1) * scoreWeight;
                            })
                            .average().orElse(0.0);

                    double sim2 = userReviewedMovies.stream()
                            .mapToDouble(reviewedMovie -> {
                                Review review = findReviewByMovie(user, reviewedMovie);
                                double scoreWeight = (review != null) ? review.getScore() : 1.0;
                                return calculateSimilarity(reviewedMovie, movie2) * scoreWeight;
                            })
                            .average().orElse(0.0);

                    return Double.compare(sim2, sim1);
                })
                .collect(Collectors.toList());
    }

    private Review findReviewByMovie(Member user, MovieInfoEntity movie) {
        // 사용자가 작성한 리뷰 중에서 해당 영화를 리뷰한 것이 있으면 찾아서 반환
        return user.getReviews().stream()
                .filter(review -> review.getMovie().getId().equals(movie.getId()))
                .findFirst().orElse(null);
    }
}